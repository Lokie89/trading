package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.UpbitAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/12/09
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitOrder implements Order {

    private final Trader simulateUpbitTrader;
    private final List<UpbitOrderRequest> orderList = new ArrayList<>();

    @Override
    public OrderResponse order(OrderRequest request) {
        // TODO : OrderRequest 를 분리해야함 -> 메서드 넣는게 아니라고 생각 필드를 들고오는게 인터페이스 성격은 아닌듯
        if (!(request instanceof UpbitOrderRequest)) {
            throw new RuntimeException();
        }
        UpbitOrderRequest upbitOrderRequest = (UpbitOrderRequest) request;

        final String client = request.getClient();
        List<AccountResponse> accounts = simulateUpbitTrader.getAccounts(client);
        accounts.add(
                UpbitAccount.of(upbitOrderRequest.getMarket().replace("KRW-", ""),
                upbitOrderRequest.getPrice() * upbitOrderRequest.getVolume())
        );
        orderList.add(upbitOrderRequest);
        return null;
    }

    @Override
    public List<OrderResponse> getOrderList(OrderRequest request) {
        return null;
    }

    @Override
    public OrderResponse cancelOrder(OrderRequest cancelRequest) {
        return null;
    }
}
