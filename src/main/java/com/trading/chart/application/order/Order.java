package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */

// TODO : 리턴타입을 포함해서 넣긴했는데 확인해야될듯
public interface Order<T extends OrderResponse> {
    T order(final OrderRequest request);
    List<T> getOrderList(final OrderRequest request);
    T cancelOrder(final OrderRequest cancelRequest);
}
