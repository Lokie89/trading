package com.trading.chart.application.order.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@Getter
public class UpbitOrderRequest implements OrderRequest, Serializable {
    private String market;
    private String side;
    private Double price;
    private Double volume;
    private String ord_type;

    @Builder
    public UpbitOrderRequest(String item, TradeType tradeType, Integer cash, Double price) {
        this.market = item;
        this.side = tradeType.getUpbitSide();
        this.price = price;
        this.volume = cash / price;
        this.ord_type = price == 0 ? tradeType.getUpbitOrderType() : "limit";
    }
}
