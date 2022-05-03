package com.trading.chart.application.candle;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.response.CandleResponses;
import com.trading.chart.application.candle.response.UpbitCandleResponse;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.common.ConvertType;
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
public class UpbitCandle implements Candle {

    private final CallAPI callAPI;

    @Override
    public CandleResponses getCandles(CandleRequest candleRequest) {
        String response = callAPI.get(candleRequest.getUrl(), HttpHeaders.EMPTY);
        UpbitCandleResponse[] candles = ConvertType.stringToType(response, UpbitCandleResponse[].class);
        return CandleResponses.of(Arrays.asList(candles));
    }

}
