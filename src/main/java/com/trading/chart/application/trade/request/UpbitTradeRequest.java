package com.trading.chart.application.trade.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.match.request.UpbitMatchRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderRequest;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
public class UpbitTradeRequest implements TradeRequest {

    private final String market;
    private final UpbitUnit unit;
    private final LocalDateTime date;
    private final Integer standard;
    private final Integer range;
    private final TradeStrategy strategy;
    private final Integer matchMin;
    private final Integer matchMax;

    private final String client;
    private final TradeType side;
    private final Double price;
    private final Double volume;

    private UpbitTradeRequest(String market, UpbitUnit unit,
                              LocalDateTime date, Integer standard,
                              Integer range, TradeStrategy strategy,
                              Integer matchMin, Integer matchMax,
                              String client, TradeType side,
                              Double price, Double volume) {
        this.market = market;
        this.unit = unit;
        this.date = date;
        this.standard = standard;
        this.range = range;
        this.strategy = strategy;
        this.matchMin = matchMin;
        this.matchMax = matchMax;
        this.client = client;
        this.side = side;
        this.price = price;
        this.volume = volume;
    }

    public static Builder builder(String market, UpbitUnit unit,
                                  TradeStrategy strategy, String client,
                                  TradeType side) {
        return new Builder(market, unit, strategy, client, side);
    }

    @Override
    public MatchRequest toMatchRequest() {
        return UpbitMatchRequest.builder(market, unit, strategy)
                .date(date)
                .standard(standard)
                .range(range)
                .matchMin(matchMin)
                .matchMax(matchMax)
                .build();
    }

    @Override
    public OrderRequest toOrderRequest() {
        return UpbitOrderRequest.builder(client, market, side)
                .price(price)
                .volume(volume)
                .build();
    }

    public static class Builder {
        private final String market;
        private final UpbitUnit unit;
        private final TradeStrategy strategy;
        private final String client;
        private final TradeType side;

        private LocalDateTime date = LocalDateTime.now();
        private Integer standard = 0;
        private Integer range = 1;
        private Integer matchMin = 0;
        private Integer matchMax = 1;

        private Double price = 1.0;
        private Double volume = 5001.0;


        private Builder(String market, UpbitUnit unit,
                        TradeStrategy strategy, String client,
                        TradeType side) {
            this.market = market;
            this.unit = unit;
            this.strategy = strategy;
            this.client = client;
            this.side = side;
        }

        public Builder date(LocalDateTime date) {
            if (Objects.nonNull(date)) {
                this.date = date;
            }
            return this;
        }

        public Builder standard(Integer standard) {
            if (Objects.nonNull(standard)) {
                this.standard = standard;
            }
            return this;
        }

        public Builder range(Integer range) {
            if (Objects.nonNull(range)) {
                this.range = range;
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

        public Builder price(Double price) {
            if (Objects.nonNull(price)) {
                this.price = price;
            }
            return this;
        }

        public Builder volume(Double volume) {
            if (Objects.nonNull(volume)) {
                this.volume = volume;
            }
            return this;
        }

        public UpbitTradeRequest build() {
            return new UpbitTradeRequest(this.market, this.unit, this.date,
                    this.standard, this.range, this.strategy,
                    this.matchMin, this.matchMax, this.client,
                    this.side, this.price, this.volume);
        }
    }
}
