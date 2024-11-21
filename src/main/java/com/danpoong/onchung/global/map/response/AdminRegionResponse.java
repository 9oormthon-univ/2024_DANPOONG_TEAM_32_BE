package com.danpoong.onchung.global.map.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class AdminRegionResponse {
    @JsonProperty("documents")
    private List<KakaoDocument> documents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoDocument {
        @JsonProperty("address_name")
        private String addressName;
    }

    // 도, 시 정보만 추출
    public String[] getStateAndCityName() {
        KakaoDocument kakaoDocument = getDocument();

        if (kakaoDocument != null && kakaoDocument.addressName != null) {
            String[] splitAddress = kakaoDocument.getAddressName().split(" ");
            log.info(Arrays.toString(splitAddress));


            if (splitAddress[1].endsWith("시")) {
                return new String[]{splitAddress[0], splitAddress[1]};
            } else {
                return new String[]{splitAddress[0], ""};
            }
        }

        return null;
    }

    // 응답의 첫 부분만 사용
    private KakaoDocument getDocument() {
        return (documents != null && !documents.isEmpty()) ? documents.get(0) : null;
    }
}
