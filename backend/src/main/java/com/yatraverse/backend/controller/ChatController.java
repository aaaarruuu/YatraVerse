package com.yatraverse.backend.controller;

import com.yatraverse.backend.dto.ChatRequest;
import com.yatraverse.backend.dto.ChatResponse;
import com.yatraverse.backend.service.AiClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AiClient aiClient;

    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        return aiClient.chat(request);
    }
}