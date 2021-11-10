package com.trading.chart.application.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@AllArgsConstructor
@Builder
public class UpbitOrderRequest implements OrderRequest {
    private String item;
    private TradeType tradeType;
    private Integer cash;
    private Double price;
}
