package com.trading.chart.application.tunnel;

import org.springframework.http.HttpHeaders;

/**
 * @author SeongRok.Oh
 * @since 2021/11/08
 */
public interface TradeAPIHeader {
    HttpHeaders getHeaders(String id);
    HttpHeaders getHeaders(String id, Object data);
}
