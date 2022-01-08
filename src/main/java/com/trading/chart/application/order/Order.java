package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderCancelRequest;
import com.trading.chart.application.order.request.OrderListRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */

public interface Order {
    OrderResponse order(final OrderRequest orderRequest);
    OrderResponses orderList(final OrderListRequest listRequest);
    OrderResponse cancelOrder(final OrderCancelRequest cancelRequest);
}
