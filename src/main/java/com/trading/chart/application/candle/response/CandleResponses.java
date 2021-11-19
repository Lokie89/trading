package com.trading.chart.application.candle.response;

import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.common.DeformedList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public class CandleResponses {
    private final DeformedList<CandleResponse> candleResponses;

    private CandleResponses(DeformedList<CandleResponse> candleResponses) {
        this.candleResponses = candleResponses;
    }

    private CandleResponses(List<CandleResponse> candleResponses) {
        this.candleResponses = DeformedList.of(candleResponses);
    }

    public static CandleResponses of(DeformedList<CandleResponse> candleResponses) {
        return new CandleResponses(candleResponses);
    }

    public static CandleResponses of(List<CandleResponse> candleResponses) {
        return new CandleResponses(candleResponses);
    }

    public ChartResponses toChart() {
        return ChartResponses.of(
                this.candleResponses.stream()
                        .map(CandleResponse::toChart)
                        .collect(Collectors.toSet())
        );
    }

    public CandleResponse get(final int index) {
        return candleResponses.get(index);
    }

    public CandleResponse getReverse(final int index) {
        return candleResponses.getReverse(index);
    }

    public int size() {
        return candleResponses.size();
    }

}
