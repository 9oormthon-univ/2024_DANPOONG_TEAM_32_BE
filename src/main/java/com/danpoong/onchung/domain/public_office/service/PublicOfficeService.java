package com.danpoong.onchung.domain.public_office.service;

import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeRequest;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeResponse;
import com.danpoong.onchung.domain.public_office.repository.PublicOfficeRepository;
import com.danpoong.onchung.global.map.api.KakaoMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicOfficeService {
    private final PublicOfficeRepository publicOfficeRepository;
    private final KakaoMap kakaoMap;

    public List<FindAroundPublicOfficeResponse> findAroundPublicOffice(FindAroundPublicOfficeRequest request) {
        // 맵 중앙 좌표를 통해 1차 필터링을 위한 도, 시 정보 확보
        Double[] middlecoordinate = request.middleCoordinate();
        String[] userStateAndCity = getStateAndCityFromCoordinate(middlecoordinate[0], middlecoordinate[1]);

        // 1차 필터링
        List<PublicOffice> publicOffices = firstFilteringPublicOffice(userStateAndCity[0], userStateAndCity[1]);

        // 범위 안의 관공서만 반환
        return publicOffices.stream()
                .filter(office -> {
                    boolean checkLongitude = office.getLongitude() > request.leftBottomLongitude() && office.getLongitude() < request.rightTopLongitude();
                    boolean checkLatitude = office.getLatitude() > request.leftBottomLatitude() && office.getLatitude() < request.rightTopLatitude();

                    return checkLongitude && checkLatitude;
                })
                .map(this::changeFindAroundPublicOffice)
                .toList();
    }

    private String[] getStateAndCityFromCoordinate(Double latitude, Double longitude) {
        return kakaoMap.getRoadAddress(latitude, longitude).getStateAndCityName();
    }

    private List<PublicOffice> firstFilteringPublicOffice(String state, String city) {
        return publicOfficeRepository.findAll()
                .stream()
                .filter(location -> {
                    boolean stateCheck = location.getState().equals(state);
                    boolean cityCheck = city == null || location.getCity().equals(city);

                    return stateCheck && cityCheck;
                }).toList();
    }

    private FindAroundPublicOfficeResponse changeFindAroundPublicOffice(PublicOffice office) {
        return FindAroundPublicOfficeResponse.builder()
                .publicOfficeName(office.getName())
                .roadAddress(office.getRoadAddress())
                .latitude(office.getLatitude())
                .longitude(office.getLongitude())
                .phoneNumber(office.getPhoneNumber())
                .build();
    }
}
