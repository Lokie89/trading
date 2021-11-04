package com.trading.chart.candleapi;

import com.trading.chart.candleapi.response.CandleResponse;
import com.trading.chart.candleapi.response.UpbitMinuteCandle;
import com.trading.chart.common.ConvertType;
import com.trading.chart.common.CustomArrayList;
import com.trading.chart.tunnel.CallAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
@RequiredArgsConstructor
@Component
public class UpbitMinuteCandleAPI implements CandleAPI {
    private final CallAPI callAPI;
    private final String url = "https://api.upbit.com/v1/candles/minutes/1?market=KRW-BTC&count=1";

    @Override
    public CustomArrayList<CandleResponse> getCandles(Integer unit) {
        String response = callAPI.get(url + "&unit=" + unit, HttpHeaders.EMPTY);
        UpbitMinuteCandle[] candles = ConvertType.stringToType(response, UpbitMinuteCandle[].class);
        return new CustomArrayList<>(Arrays.asList(candles));
    }

    @Override
    public CustomArrayList<CandleResponse> getCandles(Integer unit, Integer count) {
        return null;
    }

    @Override
    public CustomArrayList<CandleResponse> getCandles(Integer unit, String market) {
        return null;
    }

    @Override
    public CustomArrayList<CandleResponse> getCandles(Integer unit, LocalDateTime to) {
        return null;
    }
}
