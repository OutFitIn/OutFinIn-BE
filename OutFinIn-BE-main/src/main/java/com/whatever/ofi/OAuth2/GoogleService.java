package com.whatever.ofi.OAuth2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GoogleService {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String LOGIN_REDIRECT_URL;

    public String getGoogleAccessToken(String accessCode) {
        String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", "http://localhost:3000/oauth/google");
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params,String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = responseEntity.getBody();

            try {
                // ObjectMapper를 사용하여 JSON 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                // "access_token" 필드의 값을 추출
                String accessToken = jsonNode.get("access_token").asText();

                return accessToken;
            } catch (Exception e) {
                // 예외 처리
                e.printStackTrace();
            }
        }
        return null;
    }
    public String getUserInfo(String accessToken) {
        WebClient webclient = WebClient.builder()
                .baseUrl("https://www.googleapis.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v2/userinfo")
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // "id" 필드의 값을 가져옴
            String id = rootNode.get("id").asText();

            return id;
        } catch (Exception e) {
            // 예외 처리 필요
            return null;
        }
    }
}
