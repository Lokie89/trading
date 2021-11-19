package com.trading.chart.application.candle.response;

import com.trading.chart.application.chart.response.ChartResponse;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface CandleResponse {
    Integer getUnit();
    String getMarket();
    LocalDateTime getCandleDateTimeKST();
    Double getTradePrice();
    ChartResponse toChart();
}
