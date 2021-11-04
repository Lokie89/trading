package com.trading.chart.candleapi;

import com.trading.chart.candleapi.response.CandleResponse;
import com.trading.chart.common.CustomArrayList;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public interface CandleAPI {
    CustomArrayList<CandleResponse> getCandles(Integer unit);
    CustomArrayList<CandleResponse> getCandles(Integer unit, Integer count);
    CustomArrayList<CandleResponse> getCandles(Integer unit, String market);
    CustomArrayList<CandleResponse> getCandles(Integer unit, LocalDateTime to);
}
