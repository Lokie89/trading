package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
public interface Order {
    OrderResponse order(final OrderRequest request);
    List<OrderResponse> getOrderList(final OrderRequest request);

}
