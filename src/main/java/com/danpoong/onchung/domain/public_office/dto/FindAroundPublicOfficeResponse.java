package com.danpoong.onchung.domain.public_office.dto;

import lombok.Builder;

@Builder
public record FindAroundPublicOfficeResponse(
        String publicOfficeName,
        String roadAddress,

        String latitude, //위도
        String longitude, //경도

        String phoneNumber
) {
}
