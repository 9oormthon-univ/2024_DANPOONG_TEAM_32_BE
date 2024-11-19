package com.danpoong.onchung.global.map.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WTMResponse {
    @JsonProperty("documents")
    private List<KakaoDocument> documents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoDocument {
        @JsonProperty("x")
        private double x;  // 경도 (longitude)

        @JsonProperty("y")
        private double y;  // 위도 (latitude)
    }

    public double[] getCoordinates() {
        if (documents != null && !documents.isEmpty()) {
            KakaoDocument kakaoDocument = documents.get(0);
            return new double[]{kakaoDocument.getX(), kakaoDocument.getY()};
        }

        return null;
    }
}
