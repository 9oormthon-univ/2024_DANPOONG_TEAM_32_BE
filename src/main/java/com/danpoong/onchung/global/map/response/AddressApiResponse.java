package com.danpoong.onchung.global.map.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressApiResponse {
    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        private String x;
        private String y;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("road_address_name")
        private String roadAddress;

        private String phone;
    }
}
