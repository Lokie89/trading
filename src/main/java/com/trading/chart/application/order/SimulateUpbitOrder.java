package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.response.AccountResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/12/09
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitOrder implements Order<UpbitOrderResponse> {

    private final Trader simulateUpbitTrader;
    private final List<UpbitOrderResponse> orderedList = new ArrayList<>();

    private UpbitOrderRequest validateParameter(OrderRequest request) {
        // TODO : OrderRequest 를 분리해야함 -> 메서드 넣는게 아니라고 생각 필드를 들고오는게 인터페이스 성격은 아닌듯
        if (!(request instanceof UpbitOrderRequest)) {
            throw new RuntimeException();
        }
        return (UpbitOrderRequest) request;
    }

    @Override
    public UpbitOrderResponse order(OrderRequest request) {
        UpbitOrderRequest upbitOrderRequest = validateParameter(request);

        AccountResponses accounts = simulateUpbitTrader.getAccounts(upbitOrderRequest.toAccountRequest());
        accounts.apply(upbitOrderRequest);
        UpbitOrderResponse orderResponse = upbitOrderRequest.toOrderResponse();
        orderedList.add(orderResponse);
        return orderResponse;
    }

    public List<UpbitOrderResponse> getOrderList(OrderRequest request) {
        UpbitOrderRequest upbitOrderRequest = validateParameter(request);

        return this.orderedList.stream()
                .filter(orderResponse -> upbitOrderRequest.toOrderResponse().equalsOnMarketAndSide(orderResponse))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public UpbitOrderResponse cancelOrder(OrderRequest cancelRequest) {
        return null;
    }
}
