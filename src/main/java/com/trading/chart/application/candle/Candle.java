package com.trading.chart.application.candle;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.response.CandleResponses;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface Candle {
    CandleResponses getCandles(List<CandleRequest> candleRequestList);
}
