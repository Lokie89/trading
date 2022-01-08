package com.trading.chart.application.order.response;

import com.trading.chart.application.order.request.OrderRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2022/01/08
 */
public class SimulateOrderResponses {
    private final Map<String, OrderResponses> simulateOrderedList = new HashMap<>();

    public OrderResponse add(OrderRequest orderRequest) {
        final String client = orderRequest.getClient();
        OrderResponses orderedList = simulateOrderedList.get(client);
        if (Objects.isNull(orderedList)) {
            orderedList = OrderResponses.of(new ArrayList<>());
            simulateOrderedList.put(client, orderedList);
        }
        return orderedList.add(orderRequest);
    }

    public OrderResponses get(String client) {
        return simulateOrderedList.get(client);
    }

}
