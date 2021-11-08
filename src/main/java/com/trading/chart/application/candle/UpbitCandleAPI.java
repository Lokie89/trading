package com.trading.chart.application.candle;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.response.CandleResponse;
import com.trading.chart.application.candle.response.UpbitCandle;
import com.trading.chart.common.ConvertType;
import com.trading.chart.common.CustomArrayList;
import com.trading.chart.application.tunnel.CallAPI;
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
public class UpbitCandleAPI implements CandleAPI {

    private final CallAPI callAPI;

    @Override
    public CustomArrayList<CandleResponse> getCandles(CandleRequest candleRequest) {
        String response = callAPI.get(candleRequest.getUrl(), HttpHeaders.EMPTY);
        UpbitCandle[] candles = ConvertType.stringToType(response, UpbitCandle[].class);
        return new CustomArrayList<>(Arrays.asList(candles));
    }

}
