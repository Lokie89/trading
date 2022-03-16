package com.trading.chart.application.order.response;

import com.trading.chart.application.order.request.OrderState;
import com.trading.chart.domain.simulation.SimulatedOrder;

public interface OrderResponse {
    String getUuid();
    String getMarket();
    OrderState getState();
    Boolean isBuyOrder();
    String getCurrency();
    Double getVolume();
    Double getPrice();
    void log();
    SimulatedOrder toSimulatedOrder();
}
