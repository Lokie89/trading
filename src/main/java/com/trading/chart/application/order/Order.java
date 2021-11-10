package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.trader.response.OrderResponse;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
public interface Order {
    OrderResponse order(OrderRequest request);
}
