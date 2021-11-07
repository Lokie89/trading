package com.trading.chart.candleapi;

import com.trading.chart.candleapi.request.CandleRequest;
import com.trading.chart.candleapi.response.CandleResponse;
import com.trading.chart.common.CustomArrayList;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface CandleAPI {
    CustomArrayList<CandleResponse> getCandles(CandleRequest request);
}
