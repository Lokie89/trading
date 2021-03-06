package com.trading.chart.domain.user.response;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.match.request.UpbitMatchRequest;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.domain.user.ExchangePlatform;
import com.trading.chart.domain.user.TradeResource;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2022/02/02
 */
@Getter
public class TradeResourceResponse {
    private final ExchangePlatform platform;
    private final TradeType tradeType;
    private final TradeStrategy strategy;
    private final UpbitUnit unit;
    private final Integer matchStandard;
    private final Integer matchRange;
    private final Integer matchMin;
    private final Integer matchMax;

    private TradeResourceResponse(ExchangePlatform platform, TradeType tradeType, TradeStrategy strategy,
                                  UpbitUnit unit, Integer matchStandard, Integer matchRange,
                                  Integer matchMin, Integer matchMax) {
        this.platform = platform;
        this.tradeType = tradeType;
        this.strategy = strategy;
        this.unit = unit;
        this.matchStandard = matchStandard;
        this.matchRange = matchRange;
        this.matchMin = matchMin;
        this.matchMax = matchMax;
    }

    public MatchRequest toUpbitMatchRequest(String market, LocalDateTime date) {
        return UpbitMatchRequest.builder(market, unit, strategy)
                .date(date)
                .matchStandard(matchStandard)
                .matchRange(matchRange)
                .matchMin(matchMin)
                .matchMax(matchMax)
                .build();
    }

    public boolean isEqualsTradeType(TradeType tradeType) {
        return this.tradeType.equals(tradeType);
    }

    public static Builder builder(ExchangePlatform platform, TradeType tradeType,
                                  TradeStrategy strategy, UpbitUnit unit) {
        return new Builder(platform, tradeType, strategy, unit);
    }

    public TradeResource toEntity(){
        return TradeResource.builder()
                .platform(platform)
                .tradeType(tradeType)
                .strategy(strategy)
                .unit(unit)
                .matchStandard(matchStandard)
                .matchRange(matchRange)
                .matchMin(matchMin)
                .matchMax(matchMax)
                .build();
    }

    public static class Builder {
        private final ExchangePlatform platform;
        private final TradeType tradeType;
        private final TradeStrategy strategy;
        private final UpbitUnit unit;
        private Integer matchStandard = 0;
        private Integer matchRange = 1;
        private Integer matchMin = 1;
        private Integer matchMax = 1;

        private Builder(ExchangePlatform platform, TradeType tradeType, TradeStrategy strategy, UpbitUnit unit) {
            this.platform = platform;
            this.tradeType = tradeType;
            this.strategy = strategy;
            this.unit = unit;
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

        public TradeResourceResponse build() {
            return new TradeResourceResponse(platform, tradeType, strategy, unit,
                    matchStandard, matchRange, matchMin, matchMax);
        }

    }

}
