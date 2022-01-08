package com.trading.chart.application.trader;

import com.trading.chart.application.order.Order;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/12/09
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitTrader implements Trader {

    private static final Map<AccountRequest, AccountResponses> SIMULATE_ACCOUNT = new HashMap<>();
    private final Order simulateUpbitOrder;

    static {
        SIMULATE_ACCOUNT.put(UpbitAccountRequest.of("million"), AccountResponses.of(UpbitAccount.of("KRW", 1000000.0, 0.0)));
    }

    public AccountResponses getAccounts(AccountRequest request) {
        return SIMULATE_ACCOUNT.get(request);
    }

    @Override
    public OrderResponse order(OrderRequest orderRequest) {
        if (!(orderRequest instanceof UpbitOrderRequest)) {
            throw new RuntimeException();
        }
        UpbitOrderRequest upbitOrderRequest = (UpbitOrderRequest) orderRequest;
        AccountResponses accountResponses = SIMULATE_ACCOUNT.get(orderRequest.toAccountRequest());
        accountResponses.apply(upbitOrderRequest);
        return simulateUpbitOrder.order(orderRequest);
    }

}
