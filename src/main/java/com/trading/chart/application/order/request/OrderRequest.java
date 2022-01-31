package com.trading.chart.application.order.request;

import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trader.request.AccountRequest;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
public interface OrderRequest {
    String getClient();
    AccountRequest toAccountRequest();
    OrderListRequest toOrderListRequest();
    OrderResponse toOrderResponse();
    Boolean isBuyOrder();
    String getCurrency();
    Double getVolume();
    Double getPrice();
}
