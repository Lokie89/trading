package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.application.tunnel.TradeAPIHeader;
import com.trading.chart.common.ConvertType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Convert;
import java.lang.reflect.Field;
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

    private final String url = "https://api.upbit.com/v1/orders";
    private final String deleteUrl = "https://api.upbit.com/v1/order";
    private final CallAPI callAPI;
    private final TradeAPIHeader header;

    @Override
    public OrderResponse order(final OrderRequest request) {
        String response = callAPI.post(url, header.getHeaders(request.getAccount(), request), request);
        return ConvertType.stringToType(response, UpbitOrderResponse.class);
    }

    @Override
    public List<OrderResponse> getOrderList(final OrderRequest request) {
        String response = callAPI.get(url + "?" + ConvertType.ObjectToQueryString(request, "account"),
                header.getHeaders(request.getAccount(), request));
        UpbitOrderResponse[] upbitOrderResponses = ConvertType.stringToType(response, UpbitOrderResponse[].class);
        return Arrays.asList(upbitOrderResponses);
    }

    @Override
    public OrderResponse cancelOrder(final OrderRequest request) {
        String response = callAPI.delete(deleteUrl + "?" + ConvertType.ObjectToQueryString(request, "account"),
                header.getHeaders(request.getAccount(), request));
        return ConvertType.stringToType(response, UpbitOrderResponse.class);
    }
}
