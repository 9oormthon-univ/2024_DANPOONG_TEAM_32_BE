package com.danpoong.onchung.global.map.api;

import com.danpoong.onchung.global.map.response.AddressApiResponse;
import com.danpoong.onchung.global.map.response.AdminRegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoMap {
    @Value("${kakao.rest_api.key}")
    private String kakaoMapApiKey;

    // 위도 경도 -> 행정구역
    @Value("${kakao.rest_api.change_address_url}")
    private String changeAddressUrl;
    // 도로명 주소, 위도 경도 모두 받을 때 사용
    @Value("${kakao.rest_api.search_address_url}")
    private String searchAddressUrl;

    private final RestTemplate restTemplate;

    // 위도 경도 -> 행정구역
    public AdminRegionResponse getRoadAddress(String latitude, String longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(changeAddressUrl)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+ kakaoMapApiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(url, HttpMethod.GET, entity, AdminRegionResponse.class)
                .getBody();
    }

    // 관공서 이름을 통해 도로명 주소, 위도 경도 받기
    public AddressApiResponse getAddress(String search) {
        String url = UriComponentsBuilder.fromHttpUrl(searchAddressUrl)
                .queryParam("query", search)
                .queryParam("analyze_type", "exact")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+ kakaoMapApiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(url, HttpMethod.GET, entity, AddressApiResponse.class)
                .getBody();
    }
}
