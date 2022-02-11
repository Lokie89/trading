package com.trading.chart.application.trader;

import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.Trade;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.request.AccountRequest;
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

    private final Map<AccountRequest, AccountResponses> simulateAccounts = new HashMap<>();
    private final Trade simulateUpbitTrade;

    public AccountResponses getAccounts(AccountRequest request) {
        initSimulateAccounts(request);
        return simulateAccounts.get(request);
    }

    @Override
    public OrderResponse trade(TradeRequest tradeRequest) {
        AccountRequest accountRequest = tradeRequest.toAccountRequest();
        initSimulateAccounts(accountRequest);
        OrderResponse orderResponse = simulateUpbitTrade.trade(tradeRequest);
        if (Objects.nonNull(orderResponse)) {
            simulateAccounts.get(accountRequest).apply(orderResponse);
        }
        return orderResponse;
    }

    private void initSimulateAccounts(AccountRequest request) {
        if (Objects.isNull(simulateAccounts.get(request))) {
            simulateAccounts.put(request, AccountResponses.of(UpbitAccount.of("KRW", 0.0, 0.0)));
        }
    }

}
