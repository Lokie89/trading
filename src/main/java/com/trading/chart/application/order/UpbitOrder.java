package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderCancelRequest;
import com.trading.chart.application.order.request.OrderListRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.application.tunnel.TradeAPIHeader;
import com.trading.chart.common.ConvertType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */

@RequiredArgsConstructor
@Component
public class UpbitOrder implements Order {

    private final static String URL = "https://api.upbit.com/v1/orders";
    private final static String DELETE_URL = "https://api.upbit.com/v1/order";
    private final CallAPI callAPI;
    private final TradeAPIHeader header;

    @Override
    public OrderResponse order(final OrderRequest request) {
        String response = callAPI.post(URL, header.getHeaders(request.getClient(), request), request);
        return ConvertType.stringToType(response, UpbitOrderResponse.class);
    }

    @Override
    public OrderResponses orderList(final OrderListRequest request) {
        String response = callAPI.get(URL + "?" + ConvertType.ObjectToQueryString(request, "client"),
                header.getHeaders(request.getClient(), request));
        UpbitOrderResponse[] upbitOrderResponses = ConvertType.stringToType(response, UpbitOrderResponse[].class);
        return OrderResponses.of(new ArrayList<>(Arrays.asList(upbitOrderResponses)));
    }

    @Override
    public OrderResponse cancelOrder(final OrderCancelRequest request) {
        String response = callAPI.delete(DELETE_URL + "?" + ConvertType.ObjectToQueryString(request, "client"),
                header.getHeaders(request.getClient(), request));
        return ConvertType.stringToType(response, UpbitOrderResponse.class);
    }
}
