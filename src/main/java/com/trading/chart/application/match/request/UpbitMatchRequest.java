package com.trading.chart.application.match.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
@Getter
public class UpbitMatchRequest implements MatchRequest {
    private final String market;
    private final UpbitUnit unit;
    private final LocalDateTime date;
    private final Integer matchStandard;
    private final Integer matchRange;
    private final TradeStrategy tradeStrategy;
    private final Integer matchMin;
    private final Integer matchMax;

    private UpbitMatchRequest(String market,
                              UpbitUnit unit,
                              LocalDateTime date,
                              Integer matchStandard,
                              Integer matchRange,
                              TradeStrategy tradeStrategy,
                              Integer matchMin,
                              Integer matchMax) {
        this.market = market;
        this.unit = unit;
        this.date = date;
        this.matchStandard = matchStandard;
        this.matchRange = matchRange;
        this.tradeStrategy = tradeStrategy;
        this.matchMin = matchMin;
        this.matchMax = matchMax;
    }

    public static Builder builder(String market, UpbitUnit unit, TradeStrategy tradeStrategy) {
        return new Builder(market, unit, tradeStrategy);
    }

    @Override
    public ChartRequest toChartRequest() {
        LocalDateTime chartDate = unit.isNotMinuteUnit() ? date.withHour(9).withMinute(1) : date;
        return SimpleUpbitChartRequest.builder(market, unit).count(matchStandard + matchRange).to(chartDate).build();
    }

    @Override
    public boolean test(ChartResponses chart) {
        // 거래 정지 일경우 market 정보는 주지만 차트를 안줌
        if (Objects.isNull(chart)) {
            return false;
        }
        Boolean[] result = tradeStrategy.test(chart);
        final long count = Arrays.stream(result).filter(Boolean::booleanValue).count();
        return count >= matchMin && count <= matchMax;
    }

    public static class Builder {
        private final String market;
        private final UpbitUnit unit;
        private final TradeStrategy tradeStrategy;
        private LocalDateTime date = LocalDateTime.now();
        private Integer matchStandard = 0;
        private Integer matchRange = 1;
        private Integer matchMin = 1;
        private Integer matchMax = 1;

        private Builder(String market, UpbitUnit unit, TradeStrategy tradeStrategy) {
            this.market = market;
            this.unit = unit;
            this.tradeStrategy = tradeStrategy;
        }

        public Builder date(LocalDateTime date) {
            if (Objects.nonNull(date)) {
                this.date = date;
            }
            return this;
        }

        public Builder matchStandard(Integer matchStandard) {
            if (Objects.nonNull(matchStandard)) {
                this.matchStandard = matchStandard;
            }
            return this;
        }

        public Builder matchRange(Integer matchRange) {
            if (Objects.nonNull(matchRange)) {
                this.matchRange = matchRange;
            }
            return this;
        }

        public Builder matchMin(Integer matchMin) {
            if (Objects.nonNull(matchMin)) {
                this.matchMin = matchMin;
            }
            return this;
        }

        public Builder matchMax(Integer matchMax) {
            if (Objects.nonNull(matchMax)) {
                this.matchMax = matchMax;
            }
            return this;
        }

        public UpbitMatchRequest build() {
            return new UpbitMatchRequest(market, unit, date, matchStandard, matchRange, tradeStrategy, matchMin, matchMax);
        }
    }
}
