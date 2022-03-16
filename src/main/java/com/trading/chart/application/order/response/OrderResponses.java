package com.trading.chart.application.order.response;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.domain.simulation.SimulatedOrder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/12/21
 */
public class OrderResponses {
    private final List<OrderResponse> orderResponseList;

    private OrderResponses(List<OrderResponse> orderResponseList) {
        this.orderResponseList = orderResponseList;
    }

    public static OrderResponses of(List<OrderResponse> orderResponseList) {
        return new OrderResponses(orderResponseList);
    }

    public int size() {
        return orderResponseList.size();
    }

    public void add(OrderResponse orderResponse) {
        this.orderResponseList.add(orderResponse);
    }

    public OrderResponse add(OrderRequest orderRequest) {
        OrderResponse orderResponse = orderRequest.toOrderResponse();
        this.add(orderResponse);
        return orderResponse;
    }

    public void addAll(OrderResponses other) {
        this.orderResponseList.addAll(other.orderResponseList);
    }

    public void log() {
        orderResponseList.forEach(OrderResponse::log);
        System.out.println("BUY : " + orderResponseList.stream().filter(OrderResponse::isBuyOrder).count() + " 건 "
                + "SELL : " + orderResponseList.stream().filter(orderResponse -> !orderResponse.isBuyOrder()).count() + " 건 ");
    }

    public List<SimulatedOrder> toEntity() {
        return orderResponseList.stream().map(OrderResponse::toSimulatedOrder).collect(Collectors.toList());
    }
}
