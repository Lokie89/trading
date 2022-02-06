package com.trading.chart.application.order.request;

import com.trading.chart.application.order.response.OrderResponse;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
public interface OrderRequest {
    String getClient();
    OrderListRequest toOrderListRequest();
    OrderResponse toOrderResponse();
}
