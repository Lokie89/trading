package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */

@Getter
public enum TradeType {
    BUY("bid", "price"),
    SELL("ask", "market"),
    ;
    private String side;
    private String upbitOrderType;

    TradeType(String side, String upbitOrderType) {
        this.side = side;
        this.upbitOrderType = upbitOrderType;
    }

    @JsonCreator
    public static TradeType fromString(String side) {
        return Arrays.stream(values())
                .filter(tradeType -> tradeType.getSide().equals(side.toLowerCase()))
                .findAny()
                .orElseThrow()
                ;
    }

}
