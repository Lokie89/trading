package com.trading.chart.application.match.request;

import com.trading.chart.application.chart.response.ChartResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
public enum TradeStrategy {
    LOWER_BOLLINGERBANDS() {
        @Override
        public Boolean[] test(ChartResponses charts) {
            return charts.stream()
                    .map((chart) -> chart.getDownBollingerBand() >= chart.getTradePrice())
                    .toArray(Boolean[]::new)
                    ;
        }
    },
    LOWER_RSI30() {
        @Override
        public Boolean[] test(ChartResponses charts) {
            return charts.stream()
                    .map((chart) -> chart.getRsi() <= 30)
                    .toArray(Boolean[]::new)
                    ;
        }
    },
    ;

    public abstract Boolean[] test(ChartResponses charts);
}
