package com.danpoong.onchung.domain.chat_gpt.controller;

import com.danpoong.onchung.domain.chat_gpt.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api")
@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/upload")
    public String uploadExcelAndSaveToDB(@RequestParam("file") MultipartFile file) {
        try {
            return chatService.processAndSave(file);
        } catch (IOException e) {
            return "파일 처리 중 오류 발생: " + e.getMessage();
        }
    }
}
