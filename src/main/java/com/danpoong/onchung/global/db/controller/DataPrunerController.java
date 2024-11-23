package com.danpoong.onchung.global.db.controller;

import com.danpoong.onchung.global.db.service.DataPrunerService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/database")
@RequiredArgsConstructor
public class DataPrunerController {
    private final DataPrunerService dataPrunerService;

    @PatchMapping("/word")
    public ResponseTemplate<?> wordExampleAndRelatedWelfare() {
        dataPrunerService.wordExampleAndRelatedWelfare();

        return new ResponseTemplate<>(HttpStatus.OK, "불필요한 부분 삭제 성공");
    }
}
