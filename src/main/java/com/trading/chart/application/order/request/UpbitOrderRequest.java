package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@Getter
public class UpbitOrderRequest implements OrderRequest, Serializable {
    @JsonIgnore
    private final String client;
    private final String market;
    private final String side;
    private final Double price;
    private final Double volume;
    private final String ord_type;

    @Builder
    public UpbitOrderRequest(final String client,
                             final String item,
                             final TradeType tradeType,
                             final Integer cash,
                             final Double price) {
        this.client = client;
        this.market = item;
        this.side = tradeType.getSide();
        this.price = price;
        this.volume = cash / price;
        this.ord_type = price == 0 ? tradeType.getUpbitOrderType() : "limit";
    }
}
