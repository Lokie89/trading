package com.trading.chart.application.candle.response;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponses;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public class CandleResponses {
    private final List<CandleResponse> candleResponses;

    private CandleResponses(List<CandleResponse> candleResponses) {
        this.candleResponses = candleResponses;
    }

    public static CandleResponses of(List<CandleResponse> candleResponses) {
        return new CandleResponses(candleResponses);
    }

    public ChartResponses toChart(UpbitUnit unit) {
        return ChartResponses.of(
                this.candleResponses.stream()
                        .map(candle -> candle.toChart(unit))
                        .collect(Collectors.toSet())
        );
    }

    public CandleResponse get(final int index) {
        return candleResponses.get(index);
    }

    public int size() {
        return candleResponses.size();
    }

}
