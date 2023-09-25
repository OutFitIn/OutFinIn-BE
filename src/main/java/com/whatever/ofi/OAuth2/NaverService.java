package com.whatever.ofi.OAuth2;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
public class NaverService {
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String ClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String ClientSecret;

    public String getNaverAccessToken (String code) {
        WebClient webclient = WebClient.builder()
                .baseUrl("https://nid.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JSONObject response = webclient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2.0/token")
                        .queryParam("client_id", ClientId)
                        .queryParam("client_secret", ClientSecret)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("state", "")
                        .queryParam("code", code)
                        .build())
                .retrieve().bodyToMono(JSONObject.class).block();

        // 네이버에서 온 응답에서 토큰을 추출
        return response.get("access_token").toString();
    }

    public String getUserInfo(String accessToken) {
        WebClient webclient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Map<String, Object> response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/nid/me")
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // "response" 객체에서 "id" 값을 추출
        String id = (String) ((Map<String, Object>) response.get("response")).get("id");

        return id;
    }
}
