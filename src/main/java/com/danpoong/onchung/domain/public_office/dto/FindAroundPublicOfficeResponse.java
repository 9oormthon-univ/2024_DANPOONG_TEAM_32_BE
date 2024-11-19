package com.danpoong.onchung.domain.public_office.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record FindAroundPublicOfficeResponse(
        String publicOfficeName,
        String roadAddress,

        Double latitude, //위도
        Double longitude, //경도

        String phoneNumber
) {
}
