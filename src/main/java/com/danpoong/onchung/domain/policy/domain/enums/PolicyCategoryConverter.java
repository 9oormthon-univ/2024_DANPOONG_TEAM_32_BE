package com.danpoong.onchung.domain.policy.domain.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PolicyCategoryConverter implements AttributeConverter<PolicyCategory, String> {

    @Override
    public String convertToDatabaseColumn(PolicyCategory attribute) {
        return attribute != null ? attribute.getDisplayName() : null;
    }

    @Override
    public PolicyCategory convertToEntityAttribute(String dbData) {
        for (PolicyCategory category : PolicyCategory.values()) {
            if (category.getDisplayName().equals(dbData)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + dbData);
    }
}

