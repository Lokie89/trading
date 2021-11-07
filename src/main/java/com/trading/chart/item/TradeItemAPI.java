package com.trading.chart.item;

import com.trading.chart.common.CustomArrayList;
import com.trading.chart.item.response.ItemResponse;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface TradeItemAPI {
    CustomArrayList<ItemResponse> getItems();
}
