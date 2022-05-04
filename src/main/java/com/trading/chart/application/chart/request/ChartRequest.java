package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.common.ChartKey;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021-11-18
 */
public interface ChartRequest {
    CandleRequest toCandleRequest();
    LocalDateTime getTime();
    int getPeriod();
    ChartKey getRequestKey();
    int getCount();
    int getMandatoryCount();
    ChartResponse[] forWorkIndex();
    ChartResponse[] forRequestIndex();
    String getMarket();
    UpbitUnit getUnit();
    int forWorkCount();
    List<ChartRequest> toMessageRequest();
}
