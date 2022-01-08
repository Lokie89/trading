package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderCancelRequest;
import com.trading.chart.application.order.request.OrderListRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.order.response.SimulateOrderResponses;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/12/09
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitOrder implements Order {

    private final SimulateOrderResponses orderedList = new SimulateOrderResponses();

    @Override
    public OrderResponse order(OrderRequest request) {
        return orderedList.add(request);
    }

    @Override
    public OrderResponses orderList(OrderListRequest request) {
        return orderedList.get(request.getClient());
    }

    @Override
    public UpbitOrderResponse cancelOrder(OrderCancelRequest cancelRequest) {
        return null;
    }
}
