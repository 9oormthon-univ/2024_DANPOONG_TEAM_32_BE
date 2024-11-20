package com.danpoong.onchung.domain.public_office.dto;

import lombok.Builder;

@Builder
public record FindAroundPublicOfficeResponse(
        String publicOfficeName,
        String roadAddress,

        String longitude, //경도
        String latitude, //위도

        String phoneNumber
) {
}
