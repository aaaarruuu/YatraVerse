package com.yatraverse.backend.service;

import com.yatraverse.backend.dto.ChatRequest;
import com.yatraverse.backend.dto.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpClient;
import java.time.Duration;

@Service
public class AiClient {

    private static final Logger log = LoggerFactory.getLogger(AiClient.class);

    private final RestClient restClient;

    public AiClient(@Value("${ai.service.url}") String baseUrl,
                    @Value("${ai.service.key:}") String apiKey) {
        HttpClient http = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(http);
        factory.setReadTimeout(Duration.ofSeconds(120));

        RestClient.Builder builder = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory);
        if (!apiKey.isBlank()) {
            builder.defaultHeader("X-API-Key", apiKey);
        }
        this.restClient = builder.build();

        log.info("AiClient configured: baseUrl={}, apiKeyConfigured={}", baseUrl, !apiKey.isBlank());
    }

    public ChatResponse chat(ChatRequest request) {
        ChatResponse response;
        try {
            response = restClient.post()
                    .uri("/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(ChatResponse.class);
        } catch (RestClientResponseException e) {
            log.error("AI call failed: HTTP {} from AI service", e.getStatusCode().value());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "AI service unavailable");
        } catch (RestClientException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            log.error("AI call failed: {} - {}", cause.getClass().getName(), cause.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "AI service unavailable");
        }
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Empty response from AI service");
        }
        return response;
    }
}