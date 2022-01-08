package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import lombok.Getter;

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
        this.price = price;
        this.volume = Objects.isNull(volume) ? cash / price : volume;
        this.ord_type = Objects.isNull(price) ? tradeType.getUpbitOrderType() : "limit";
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

    public static class Builder {
        private final String client;
        private final String item;
        private final TradeType tradeType;

        private Integer cash;
        private Double price;
        private Double volume;
        private String orderType;

        private Builder(String client, String item, TradeType tradeType) {
            this.client = client;
            this.item = item;
            this.tradeType = tradeType;
        }

        public Builder cash(Integer cash) {
            this.cash = cash;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder volume(Double volume) {
            this.volume = volume;
            return this;
        }

        public Builder orderType(String orderType) {
            this.orderType = orderType;
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
}
