package com.trading.chart.tunnel;

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
        log.info(url);
        String response = null;
        try {
            response = restTemplate.exchange(url, httpMethod, new HttpEntity<>(body, httpHeaders), String.class).getBody();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return response;
    }

}