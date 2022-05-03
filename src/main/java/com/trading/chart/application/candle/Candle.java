package com.trading.chart.application.candle;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.response.CandleResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface Candle {
    CandleResponses getCandles(CandleRequest candleRequest);
}
