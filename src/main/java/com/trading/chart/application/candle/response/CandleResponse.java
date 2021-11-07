package com.trading.chart.application.candle.response;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface CandleResponse {
    Integer getUnit();
    String getMarket();
    LocalDateTime getCandleDateTimeKST();
}
