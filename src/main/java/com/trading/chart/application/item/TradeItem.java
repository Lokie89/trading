package com.trading.chart.application.item;

import com.trading.chart.common.CustomArrayList;
import com.trading.chart.application.item.response.ItemResponse;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface TradeItem {
    CustomArrayList<ItemResponse> getItems();
}
