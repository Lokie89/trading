package com.trading.chart.application.match.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
@Getter
public class UpbitMatchRequest implements MatchRequest {
    private final UpbitUnit unit;
    private final TradeStrategy tradeStrategy;
    private final LocalDateTime date;
    private final Integer standard;
    private final Integer range;
    private final Integer matchMin;
    private final Integer matchMax;

    private UpbitMatchRequest(UpbitUnit unit,
                              TradeStrategy tradeStrategy,
                              LocalDateTime date,
                              Integer standard,
                              Integer range,
                              Integer matchMin,
                              Integer matchMax) {
        this.unit = unit;
        this.tradeStrategy = tradeStrategy;
        this.date = date;
        this.standard = standard;
        this.range = range;
        this.matchMin = matchMin;
        this.matchMax = matchMax;
    }

    public static Builder builder(UpbitUnit unit, TradeStrategy tradeStrategy) {
        return new Builder(unit, tradeStrategy);
    }

    public static class Builder {
        private final UpbitUnit unit;
        private final TradeStrategy tradeStrategy;
        private LocalDateTime date;
        private Integer standard;
        private Integer range;
        private Integer matchMin;
        private Integer matchMax;

        private Builder(UpbitUnit unit, TradeStrategy tradeStrategy) {
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
            return new UpbitMatchRequest(unit, tradeStrategy, date, standard, range, matchMin, matchMax);
        }
    }
}
