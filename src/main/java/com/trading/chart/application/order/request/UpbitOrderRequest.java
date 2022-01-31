package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import com.trading.chart.application.trade.Trade;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@Getter
public class UpbitOrderRequest implements OrderRequest {
    @JsonIgnore
    private final String client;
    private final String market;
    private final String side;
    private final Double price;
    private final Double volume;
    private final String ord_type;

    private UpbitOrderRequest(final String client,
                              final String item,
                              final TradeType tradeType,
                              final Integer cash,
                              final Double price,
                              final Double volume) {
        this.client = client;
        this.market = item;
        this.side = tradeType.getSide();
        this.price = TradeType.BUY.equals(tradeType) ?
                (Objects.isNull(price) ? Double.valueOf(cash) : price) :
                (Objects.isNull(cash) ? null : cash / volume);
        this.volume = TradeType.BUY.equals(tradeType) ?
                (Objects.isNull(price) ? null : cash / price)
                : volume;
        this.ord_type = Objects.nonNull(price) && Objects.nonNull(volume) ? "limit" : tradeType.getUpbitOrderType();
        validateTradeResources(tradeType);
    }

    public static Builder builder(String client, String item, TradeType tradeType) {
        return new Builder(client, item, tradeType);
    }

    @Override
    public AccountRequest toAccountRequest() {
        return UpbitAccountRequest.of(client);
    }

    @Override
    public OrderListRequest toOrderListRequest() {
        return UpbitOrderListRequest.builder()
                .client(client)
                .market(market)
                .build();
    }

    private void validateTradeResources(TradeType tradeType) {
        if (TradeType.BUY.equals(tradeType) && Objects.isNull(this.price)) {
            throw new RuntimeException(); // TODO : Custom Exception
        }
        if (TradeType.SELL.equals(tradeType) && Objects.isNull(this.volume)) {
            throw new RuntimeException(); // TODO : Custom Exception
        }
    }

    public static class Builder {
        private final String client;
        private final String item;
        private final TradeType tradeType;

        private Integer cash;
        private Double price;
        private Double volume;

        private Builder(String client, String item, TradeType tradeType) {
            this.client = client;
            this.item = item;
            this.tradeType = tradeType;
            if (TradeType.BUY.equals(tradeType)) {
                this.cash = 5000;
            }
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

        public UpbitOrderRequest build() {
            return new UpbitOrderRequest(client, item, tradeType, cash, price, volume);
        }
    }

    @Override
    public UpbitOrderResponse toOrderResponse() {
        return new UpbitOrderResponse("", TradeType.fromString(side), ord_type,
                price, UpbitOrderState.DONE, market, LocalDateTime.now().toString(), volume);
    }

    @Override
    public Boolean isBuyOrder() {
        return TradeType.BUY.equals(TradeType.fromString(side));
    }

    @Override
    public String getCurrency() {
        return market.replace("KRW-","");
    }
}
