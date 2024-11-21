package com.danpoong.onchung.domain.public_office.service;

import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeRequest;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeResponse;
import com.danpoong.onchung.domain.public_office.repository.PublicOfficeRepository;
import com.danpoong.onchung.global.map.api.KakaoMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PublicOfficeService {
    private final PublicOfficeRepository publicOfficeRepository;
    private final KakaoMap kakaoMap;

    // 띄운 맵의 범위 안 관공서 조회
    public List<FindAroundPublicOfficeResponse> findAroundPublicOffice(FindAroundPublicOfficeRequest request) {
        // 맵 중앙 좌표를 통해 1차 필터링을 위한 도, 시 정보 확보
        String[] middleCoordinate = request.middleCoordinate();
        String[] userStateAndCity = getStateAndCityFromCoordinate(middleCoordinate[0], middleCoordinate[1]);
        log.info(Arrays.toString(userStateAndCity));

        // 1차 필터링
        List<PublicOffice> publicOffices = firstFilteringPublicOffice(userStateAndCity[0], userStateAndCity[1]);
        log.info(publicOffices.toString());

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
//    @Transactional
//    public void saveAddress() {
//        List<PublicOffice> publicOffices = publicOfficeRepository.findAll();
//
//        publicOffices.forEach(publicOffice -> {
//            AddressApiResponse addressApiResponse = kakaoMap.getAddress(publicOffice.getName());
//
//            if (addressApiResponse == null || addressApiResponse.getDocuments().isEmpty()) {
//                return;
//            }
//
//            Optional<AddressApiResponse.Document> targetDocument = addressApiResponse.getDocuments()
//                    .stream()
//                    .findFirst();
//
//            targetDocument.ifPresent(document ->
//                    publicOffice.updateInfo(document.getRoadAddress(), document.getX(), document.getY(), document.getPhone())
//            );
//        });
//    }



    private String[] getStateAndCityFromCoordinate(String longitude, String latitude) {
        return kakaoMap.getAdminDistrict(longitude, latitude).getStateAndCityName();
    }

    private List<PublicOffice> firstFilteringPublicOffice(String state, String city) {
        List<PublicOffice> publicOffices = publicOfficeRepository.findAll();
        log.info(publicOffices.toString());

        return publicOfficeRepository.findAll()
                .stream()
                .filter(location -> {
                    if (location.getCity().isEmpty()) {
                        return location.getState().contains(state);
                    } else {
                        boolean stateCheck = location.getState().contains(state);
                        boolean cityCheck = city.isEmpty() || location.getCity().contains(city);

                        return stateCheck && cityCheck;
                    }
                }).toList();
    }

    private FindAroundPublicOfficeResponse changeFindAroundPublicOffice(PublicOffice office) {
        return FindAroundPublicOfficeResponse.builder()
                .publicOfficeName(office.getName())
                .roadAddress(office.getRoadAddress())
                .longitude(office.getLongitude())
                .latitude(office.getLatitude())
                .phoneNumber(office.getPhoneNumber())
                .build();
    }
}
