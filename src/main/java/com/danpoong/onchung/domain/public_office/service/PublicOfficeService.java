package com.danpoong.onchung.domain.public_office.service;

import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeRequest;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeResponse;
import com.danpoong.onchung.domain.public_office.repository.PublicOfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PublicOfficeService {
    private final PublicOfficeRepository publicOfficeRepository;

    // 띄운 맵의 범위 안 관공서 조회
    public List<FindAroundPublicOfficeResponse> findAroundPublicOffice(FindAroundPublicOfficeRequest request) {
        double leftBottomLongitude = Double.parseDouble(request.leftBottomLongitude());
        double rightTopLongitude = Double.parseDouble(request.rightTopLongitude());
        double leftBottomLatitude = Double.parseDouble(request.leftBottomLatitude());
        double rightTopLatitude = Double.parseDouble(request.rightTopLatitude());

        List<PublicOffice> offices = publicOfficeRepository.findInArea(
                leftBottomLongitude, rightTopLongitude, leftBottomLatitude, rightTopLatitude
        );

        log.info(offices.toString());

        return offices.stream()
                .map(this::changeFindAroundPublicOffice)
                .collect(Collectors.toList());
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
