package com.trading.chart.application.item;

import com.trading.chart.application.item.response.ItemResponse;

import java.util.SortedSet;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface TradeItem {
    SortedSet<ItemResponse> getItems();
    SortedSet<ItemResponse> getKrwItems();
    void update();
}
