package com.trading.chart.application.tunnel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class CallAPI {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String get(String url, HttpHeaders httpHeaders) {
        return callApiEndpoint(url, HttpMethod.GET, httpHeaders, null);
    }

    public String post(String url, HttpHeaders httpHeaders, Object body) {
        return callApiEndpoint(url, HttpMethod.POST, httpHeaders, body);
    }

    public String delete(String url, HttpHeaders httpHeaders, Object body) {
        return callApiEndpoint(url, HttpMethod.DELETE, httpHeaders, body);
    }

    private String callApiEndpoint(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Object body) {
        log.info("CallAPI.callApiEndpoint url : " + url);
        String response = null;
        try {
            String jsonValue = objectMapper.writeValueAsString(body);
            response = restTemplate.exchange(url, httpMethod, new HttpEntity<>(jsonValue, httpHeaders), String.class).getBody();
            log.info("CallAPI.callApiEndpoint response : " + response);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

}