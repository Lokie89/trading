package com.trading.chart.application.order.request;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@Getter
public enum TradeType {
    BUY("bid", "price"),
    SELL("ask", "market"),
    ;
    private String upbitSide;
    private String upbitOrderType;

    TradeType(String upbitSide, String upbitOrderType) {
        this.upbitSide = upbitSide;
        this.upbitOrderType = upbitOrderType;
    }

}
