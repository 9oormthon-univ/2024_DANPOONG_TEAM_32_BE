package com.danpoong.onchung.global.csv.controller;

import com.danpoong.onchung.global.csv.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/csv")
public class CsvController {
    private final CsvService csvService;

//    @PostMapping("/csv/policy")
//    public ResponseTemplate<?> readCsvAndSaveInfo(@RequestParam("file") MultipartFile file) {
//        csvService.loadDataFromCSV(file);
//        return new ResponseTemplate<>(HttpStatus.CREATED, "csv 파일 저장 성공");
//    }
}
