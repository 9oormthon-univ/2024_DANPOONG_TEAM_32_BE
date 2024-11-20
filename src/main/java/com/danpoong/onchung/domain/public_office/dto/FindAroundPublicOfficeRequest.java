package com.danpoong.onchung.domain.public_office.dto;

public record FindAroundPublicOfficeRequest(
        String leftBottomLatitude, //위도
        String leftBottomLongitude, //경도

        String rightTopLatitude,
        String rightTopLongitude
) {
    public String[] middleCoordinate() {
        double middleLatitude = (Double.parseDouble(this.leftBottomLatitude) + Double.parseDouble(this.rightTopLatitude)) / 2;
        double middleLongitude = (Double.parseDouble(this.leftBottomLongitude) + Double.parseDouble(this.rightTopLongitude)) / 2;

        return new String[]{
                String.valueOf(middleLatitude),
                String.valueOf(middleLongitude)
        };
    }
}
