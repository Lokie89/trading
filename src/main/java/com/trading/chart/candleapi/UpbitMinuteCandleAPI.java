package com.trading.chart.candleapi;

import com.trading.chart.candleapi.request.CandleRequest;
import com.trading.chart.candleapi.response.CandleResponse;
import com.trading.chart.candleapi.response.UpbitMinuteCandle;
import com.trading.chart.common.ConvertType;
import com.trading.chart.common.CustomArrayList;
import com.trading.chart.tunnel.CallAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
@RequiredArgsConstructor
@Component
public class UpbitMinuteCandleAPI implements CandleAPI {

    private final CallAPI callAPI;

    @Override
    public CustomArrayList<CandleResponse> getCandles(CandleRequest candleRequest) {
        String response = callAPI.get(candleRequest.getUrl(), HttpHeaders.EMPTY);
        UpbitMinuteCandle[] candles = ConvertType.stringToType(response, UpbitMinuteCandle[].class);
        return new CustomArrayList<>(Arrays.asList(candles));
    }

}
