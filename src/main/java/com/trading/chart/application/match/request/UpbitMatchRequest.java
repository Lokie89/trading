package com.trading.chart.application.match.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.chart.request.UpbitChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.application.match.response.MatchResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
@Getter
public class UpbitMatchRequest implements MatchRequest {
    private final String market;
    private final UpbitUnit unit;
    private final TradeStrategy tradeStrategy;
    private final LocalDateTime date;
    private final Integer standard;
    private final Integer range;
    private final Integer matchMin;
    private final Integer matchMax;

    private UpbitMatchRequest(String market,
                              UpbitUnit unit,
                              TradeStrategy tradeStrategy,
                              LocalDateTime date,
                              Integer standard,
                              Integer range,
                              Integer matchMin,
                              Integer matchMax) {
        this.market = market;
        this.unit = unit;
        this.tradeStrategy = tradeStrategy;
        this.date = date;
        this.standard = standard;
        this.range = range;
        this.matchMin = matchMin;
        this.matchMax = matchMax;
    }

    public static Builder builder(String market, UpbitUnit unit, TradeStrategy tradeStrategy) {
        return new Builder(market, unit, tradeStrategy);
    }

    @Override
    public ChartRequest toChartRequest() {
        return SimpleUpbitChartRequest.builder(market, unit).count(standard + range).to(date).build();
    }

    @Override
    public boolean test(ChartResponses chart) {
        Boolean[] result = tradeStrategy.test(chart);
        final long count = Arrays.stream(result).filter(Boolean::booleanValue).count();
        return count >= matchMin && count <= matchMax;
    }

    public static class Builder {
        private final String market;
        private final UpbitUnit unit;
        private final TradeStrategy tradeStrategy;
        private LocalDateTime date;
        private Integer standard;
        private Integer range;
        private Integer matchMin;
        private Integer matchMax;

        private Builder(String market, UpbitUnit unit, TradeStrategy tradeStrategy) {
            this.market = market;
            this.unit = unit;
            this.tradeStrategy = tradeStrategy;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder standard(Integer standard) {
            this.standard = standard;
            return this;
        }

        public Builder range(Integer range) {
            this.range = range;
            return this;
        }

        public Builder matchMin(Integer matchMin) {
            this.matchMin = matchMin;
            return this;
        }

        public Builder matchMax(Integer matchMax) {
            this.matchMax = matchMax;
            return this;
        }

        public UpbitMatchRequest build() {
            return new UpbitMatchRequest(market, unit, tradeStrategy, date, standard, range, matchMin, matchMax);
        }
    }
}
