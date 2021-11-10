package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.trader.response.OrderResponse;
import com.trading.chart.application.trader.response.UpbitOrderResponse;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.application.tunnel.TradeAPIHeader;
import com.trading.chart.common.ConvertType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */

@RequiredArgsConstructor
@Component
public class UpbitOrder implements Order {

    private final String url = "https://api.upbit.com/v1/orders";
    private final CallAPI callAPI;
    private final TradeAPIHeader header;

    @Override
    public OrderResponse order(OrderRequest request) {
        String response = callAPI.post(url, header.getHeaders("tjdfhrdk10@naver.com", request), request);
        UpbitOrderResponse upbitOrderResponse = ConvertType.stringToType(response, UpbitOrderResponse.class);
        return upbitOrderResponse;
    }
}
