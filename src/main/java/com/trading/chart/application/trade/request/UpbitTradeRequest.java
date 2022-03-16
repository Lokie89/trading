package com.trading.chart.application.trade.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
public class UpbitTradeRequest implements TradeRequest {

    @Getter
    private final String client;
    private final TradeType side;
    private final Integer cash;
    private final Double price;
    private final Double volume;

    @Getter
    private final String market;
    private final LocalDateTime date;
    private final List<TradeResourceResponse> tradeResources;
    private final AccountResponses accountResponses;


    private UpbitTradeRequest(String client, TradeType side,
                              Integer cash, Double price, Double volume,
                              String market, LocalDateTime date,
                              List<TradeResourceResponse> tradeResources,
                              AccountResponses accountResponses) {
        this.client = client;
        this.side = side;
        this.cash = cash;
        this.price = price;
        this.volume = volume;
        this.market = market;
        this.date = date;
        this.tradeResources = tradeResources;
        this.accountResponses = accountResponses;
    }

    public static Builder builder(String client, TradeType side, String market, AccountResponses accounts) {
        return new Builder(client, side, market, accounts);
    }

    @Override
    public List<MatchRequest> toMatchRequests() {
        return tradeResources.stream()
                .map(tradeResource -> tradeResource.toUpbitMatchRequest(market, date))
                .collect(Collectors.toList());
    }

    @Override
    public OrderRequest toOrderRequest(Double marketPrice) {
        UpbitOrderRequest.Builder builder = UpbitOrderRequest.builder(client, market, side)
                .orderDate(date)
                .cash(cash).price(marketPrice);
        if (isBuyOrder()) {
            return builder.volume(cash / marketPrice).build();
        }
        return builder.volume(Objects.requireNonNull(accountResponses.getAccount(market).orElse(null)).getBalance()).build();
    }

    @Override
    public ChartRequest toOrderChartRequest() {
        Optional<UpbitUnit> lessUnit = tradeResources.stream().map(TradeResourceResponse::getUnit).sorted().findFirst();
        return lessUnit.map(unit -> SimpleUpbitChartRequest.builder(market, unit)
                .to(date)
                .build()).orElse(null);
    }

    @Override
    public AccountRequest toAccountRequest() {
        return UpbitAccountRequest.of(client);
    }

    @Override
    public boolean isBuyOrder() {
        return TradeType.BUY.equals(side);
    }

    @Override
    public boolean isSellOrder() {
        return TradeType.SELL.equals(side);
    }

    @Override
    public boolean isLessPrice(AccountResponse account) {
        return this.cash < account.getBalance();
    }

    public static class Builder {
        private final String client;
        private final TradeType side;
        private Integer cash;
        private Double price;
        private Double volume;

        private final String market;
        private LocalDateTime date = LocalDateTime.now();
        private List<TradeResourceResponse> tradeResources = new ArrayList<>();
        private AccountResponses accounts;


        private Builder(String client, TradeType side,
                        String market, AccountResponses accounts) {
            this.client = client;
            this.side = side;
            this.market = market;
            this.accounts = accounts;
        }

        public Builder date(LocalDateTime date) {
            if (Objects.nonNull(date)) {
                this.date = date;
            }
            return this;
        }

        public Builder cash(Integer cash) {
            if (Objects.nonNull(cash)) {
                this.cash = cash;
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

        public Builder tradeResources(List<TradeResourceResponse> tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = tradeResources;
            }
            return this;
        }

        public Builder tradeResources(TradeResourceResponse... tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = Arrays.asList(tradeResources);
            }
            return this;
        }

        public UpbitTradeRequest build() {
            return new UpbitTradeRequest(this.client, this.side, this.cash, this.price,
                    this.volume, this.market, this.date, this.tradeResources, this.accounts);
        }
    }
}
