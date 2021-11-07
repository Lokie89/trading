package com.trading.chart.candle;

import com.trading.chart.candle.request.CandleRequest;
import com.trading.chart.candle.response.CandleResponse;
import com.trading.chart.common.CustomArrayList;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface CandleAPI {
    CustomArrayList<CandleResponse> getCandles(CandleRequest request);
}
