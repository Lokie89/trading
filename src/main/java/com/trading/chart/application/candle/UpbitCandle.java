package com.trading.chart.application.candle;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.response.CandleResponse;
import com.trading.chart.application.candle.response.CandleResponses;
import com.trading.chart.application.candle.response.UpbitCandleResponse;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.common.ConvertType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
@RequiredArgsConstructor
@Component
public class UpbitCandle implements Candle {

    private final CallAPI callAPI;

    @Override
    public CandleResponses getCandles(List<CandleRequest> candleRequestList) {
        List<CandleResponse> candleResponseList = new ArrayList<>();
        candleRequestList.forEach(candleRequest -> {
            String response = callAPI.get(candleRequest.getUrl(), HttpHeaders.EMPTY);
            UpbitCandleResponse[] candles = ConvertType.stringToType(response, UpbitCandleResponse[].class);
            candleResponseList.addAll(Arrays.asList(candles));
        });
        return CandleResponses.of(candleResponseList);
    }

}
