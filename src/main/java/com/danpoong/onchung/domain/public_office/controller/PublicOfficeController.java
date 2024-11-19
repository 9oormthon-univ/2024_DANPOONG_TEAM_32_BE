package com.danpoong.onchung.domain.public_office.controller;

import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeRequest;
import com.danpoong.onchung.domain.public_office.dto.FindAroundPublicOfficeResponse;
import com.danpoong.onchung.domain.public_office.service.PublicOfficeService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public-offices")
@RequiredArgsConstructor
@Tag(name = "Public Office Controller", description = "Public Office API")
public class PublicOfficeController {
    private final PublicOfficeService publicOfficeService;

    @Operation(summary = "근처 관공서 반환")
    @GetMapping("/nearby")
    public ResponseTemplate<List<FindAroundPublicOfficeResponse>> nearbyPublicOffices(@RequestBody FindAroundPublicOfficeRequest request) {
        return new ResponseTemplate<>(HttpStatus.OK, "근처 관공서 조회 성공", publicOfficeService.findAroundPublicOffice(request));
    }
}
