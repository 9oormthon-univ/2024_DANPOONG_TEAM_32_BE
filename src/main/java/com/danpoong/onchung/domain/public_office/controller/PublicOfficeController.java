package com.danpoong.onchung.domain.public_office.controller;

import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeRequest;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeResponse;
import com.danpoong.onchung.domain.public_office.service.PublicOfficeService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public-offices")
@RequiredArgsConstructor
@Tag(name = "Public Office Controller", description = "Public Office API")
@Slf4j
public class PublicOfficeController {
    private final PublicOfficeService publicOfficeService;

    @Operation(summary = "근처 관공서 반환")
    @GetMapping("/nearby")
    public ResponseTemplate<List<FindAroundPublicOfficeResponse>> nearbyPublicOffices(
            @RequestParam String leftBottomLongitude,
            @RequestParam String leftBottomLatitude,
            @RequestParam String rightTopLongitude,
            @RequestParam String rightTopLatitude
    ) {
        FindAroundPublicOfficeRequest request = FindAroundPublicOfficeRequest.builder()
                .leftBottomLongitude(leftBottomLongitude)
                .leftBottomLatitude(leftBottomLatitude)
                .rightTopLongitude(rightTopLongitude)
                .rightTopLatitude(rightTopLatitude)
                .build();

        return new ResponseTemplate<>(HttpStatus.OK, "근처 관공서 조회 성공", publicOfficeService.findAroundPublicOffice(request));
    }

//    @Operation(summary = "관공서 위치 정보 저장", description = "데이터 전처리 용도로 사용할 것")
//    @PatchMapping("/save-address")
//    public ResponseTemplate<?> publicOfficeSaveAddress() {
//        publicOfficeService.saveAddress();
//        return new ResponseTemplate<>(HttpStatus.OK, "관공서 위치 정보 저장 - 데이터 전처리 용도");
//    }
}
