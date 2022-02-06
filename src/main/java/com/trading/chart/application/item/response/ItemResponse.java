package com.trading.chart.application.item.response;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface ItemResponse {
    String getName();
    ItemStatus getStatus();
    boolean isKrwMarket();
}
