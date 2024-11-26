//package com.danpoong.onchung.domain.word.service;
//
//import com.danpoong.onchung.domain.word.domain.Word;
//import com.danpoong.onchung.domain.word.repository.WordRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//public class ChatService {
//    private final OpenAiChatModel openAiChatModel;
//    private final WordRepository wordRepository;
//
//    public ChatService(OpenAiChatModel openAiChatModel, WordRepository wordRepository) {
//        this.openAiChatModel = openAiChatModel;
//        this.wordRepository = wordRepository;
//    }
//
//    public String processAndSave(MultipartFile file) throws IOException {
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트
//        List<Word> termsToSave = new ArrayList<>();
//
//        // 데이터 읽기 및 카테고리별 용어 분류
//        for (Row row : sheet) {
//            if (row.getRowNum() == 0) continue; // 첫 번째 행 제외
//
//            if (isRowEmpty(row)) {
//                log.info("빈 행을 발견하여 처리를 종료합니다.");
//                break; // 빈 행이 발견되면 반복 종료
//            }
//
//            Cell categoryCell = row.getCell(0); // 첫 번째 열: 카테고리
//            Cell termCell = row.getCell(1); // 두 번째 열: 용어
//
//            if (categoryCell != null && termCell != null) {
//                String category = categoryCell.getStringCellValue();
//                log.info(category);
//                String term = termCell.getStringCellValue();
//
//                // OpenAI 호출로 요약, 예시, 관련 복지 정보 생성
////                String prompt = "용어 '" + term + "'에 대해 고등학생도 이해할 수 있는 수준으로 3줄 이내로 설명해주고, 예시를 들고 줄바꿈을 하고 관련 복지 정보를 제공해주세요.";
//
//                String prompt = "용어 '" + term + "'에 대해 3줄 이내로 고등학생도 이해할 수 있도록 설명해 주세요. 또한, 용어의 예시 단어 2개를 쉼표로 구분하여 제공하고, 관련된 복지를 단어로 제공해 주세요. 응답은 '='로만 구분하여 주세요.";
//
//
//                String response = openAiChatModel.call(prompt);
//
//                // 응답을 파싱하여 요약, 예시, 관련 복지 정보 추출
//                String[] responseParts = response.split("="); // 응답을 /로 분리
//                String description = responseParts.length > 0 ? deleteSpace(responseParts[0]) : "";
//                String example = responseParts.length > 1 ? deleteSpace(responseParts[1]) : "";
//                String relatedWelfare = responseParts.length > 2 ? deleteSpace(responseParts[2]) : "";
//
//                // 생성자를 사용해 엔티티 생성
//                Word word = new Word(category, term, description, example, relatedWelfare);
//                termsToSave.add(word);
//            }
//        }
//
//        // 데이터베이스에 저장
//        wordRepository.saveAll(termsToSave);
//
//        workbook.close();
//        return "데이터가 성공적으로 처리되어 저장되었습니다.";
//    }
//
//    private boolean isRowEmpty(Row row) {
//        // 행의 모든 셀을 확인하여 비어있는지 체크
//        for (Cell cell : row) {
//            if (cell != null && !cell.toString().trim().isEmpty()) {
//                return false; // 셀 중 하나라도 데이터가 있으면 비지 않은 행
//            }
//        }
//        return true; // 모든 셀이 비어 있으면 빈 행
//    }
//
//    private String deleteSpace(String input) {
//        if (input.startsWith(" ")) {
//            return input.substring(1);
//        } else if (input.endsWith(" ")) {
//            return input.substring(0, input.length() - 1);
//        }
//
//        return input;
//    }
//}
