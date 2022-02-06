package com.trading.chart.application.trader;

import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.Trade;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/12/09
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitTrader implements Trader {

    private static final Map<AccountRequest, AccountResponses> SIMULATE_ACCOUNT = new HashMap<>();
    private final Trade simulateUpbitTrade;

    static {
        SIMULATE_ACCOUNT.put(UpbitAccountRequest.of("million"), AccountResponses.of(UpbitAccount.of("KRW", 1000000.0, 0.0)));
    }

    public AccountResponses getAccounts(AccountRequest request) {
        return SIMULATE_ACCOUNT.get(request);
    }

    @Override
    public OrderResponse trade(TradeRequest tradeRequest) {
        AccountResponses accountResponses = SIMULATE_ACCOUNT.get(tradeRequest.toAccountRequest());
        OrderResponse orderResponse = simulateUpbitTrade.trade(tradeRequest);
        if (Objects.nonNull(orderResponse)) {
            accountResponses.apply(orderResponse);
        }
        return orderResponse;
    }

}
