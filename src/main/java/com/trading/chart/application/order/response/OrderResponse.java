package com.trading.chart.application.order.response;

import com.trading.chart.application.order.request.OrderState;

public interface OrderResponse {
    String getUuid();
    String getMarket();
    OrderState getState();
}
