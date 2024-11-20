package com.danpoong.onchung.domain.public_office.service;

import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeRequest;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeResponse;
import com.danpoong.onchung.domain.public_office.repository.PublicOfficeRepository;
import com.danpoong.onchung.global.map.api.KakaoMap;
import com.danpoong.onchung.global.map.response.AddressApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicOfficeService {
    private final PublicOfficeRepository publicOfficeRepository;
    private final KakaoMap kakaoMap;

    // 띄운 맵의 범위 안 관공서 조회
    public List<FindAroundPublicOfficeResponse> findAroundPublicOffice(FindAroundPublicOfficeRequest request) {
        // 맵 중앙 좌표를 통해 1차 필터링을 위한 도, 시 정보 확보
        String[] middleCoordinate = request.middleCoordinate();
        String[] userStateAndCity = getStateAndCityFromCoordinate(middleCoordinate[0], middleCoordinate[1]);

        // 1차 필터링
        List<PublicOffice> publicOffices = firstFilteringPublicOffice(userStateAndCity[0], userStateAndCity[1]);

        // 범위 안의 관공서만 반환
        return publicOffices.stream()
                .filter(office -> Double.parseDouble(office.getLongitude()) > Double.parseDouble(request.leftBottomLongitude()) &&
                        Double.parseDouble(office.getLongitude()) < Double.parseDouble(request.rightTopLongitude()) &&
                        Double.parseDouble(office.getLatitude()) > Double.parseDouble(request.leftBottomLatitude()) &&
                        Double.parseDouble(office.getLatitude()) < Double.parseDouble(request.rightTopLatitude()))
                .map(this::changeFindAroundPublicOffice)
                .toList();
    }

    // 관공서 주소를 정할 때 사용할 예정 - 데이터 전처리 용도
    public void saveAddress() {
        publicOfficeRepository.findAll().stream()
                .map(publicOffice -> {
                    AddressApiResponse addressApiResponse = kakaoMap.getAddress(publicOffice.getName());

                    if (addressApiResponse == null || addressApiResponse.getDocuments().isEmpty()) {
                        return null;
                    }

                    AddressApiResponse.Document document = addressApiResponse.getDocuments().get(0);

                    publicOffice.updateAddress(document.getRoadAddress().getAddressName(), document.getX(), document.getY());

                    return publicOffice;
                });
    }


    private String[] getStateAndCityFromCoordinate(String latitude, String longitude) {
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
