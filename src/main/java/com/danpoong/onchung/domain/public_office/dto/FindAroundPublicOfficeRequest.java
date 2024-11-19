package com.danpoong.onchung.domain.public_office.dto;

import lombok.Getter;

@Getter
public record FindAroundPublicOfficeRequest(
        Double leftBottomLatitude, //위도
        Double leftBottomLongitude, //경도

        Double rightTopLatitude,
        Double rightTopLongitude
) {
    public Double[] middleCoordinate() {
        return new Double[]{
                (this.leftBottomLatitude + this.rightTopLatitude) / 2,
                (this.leftBottomLongitude + this.rightTopLongitude) / 2
        };
    }
}
