package com.danpoong.onchung.domain.word.service;

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
//            Cell categoryCell = row.getCell(0); // 첫 번째 열: 카테고리
//            Cell termCell = row.getCell(1); // 두 번째 열: 용어
//
//            if (categoryCell != null && termCell != null) {
//                String category = categoryCell.getStringCellValue();
//                String term = termCell.getStringCellValue();
//
//                // OpenAI 호출로 요약, 예시, 관련 복지 정보 생성
//                String prompt = "용어 '" + term + "'에 대해 고등학생도 이해할 수 있는 수준으로 3줄 이내로 설명해주고, 예시를 들고 줄바꿈을 하고 관련 복지 정보를 제공해주세요.";
//                String response = openAiChatModel.call(prompt);
//
//                // 응답을 파싱하여 요약, 예시, 관련 복지 정보 추출
//                String[] responseParts = response.split("\n"); // 응답을 줄바꿈으로 분리
//                String description = responseParts.length > 0 ? responseParts[0] : "";
//                String example = responseParts.length > 1 ? responseParts[1] : "";
//                String relatedWelfare = responseParts.length > 2 ? responseParts[2] : "";
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
//}
