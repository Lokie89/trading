package com.trading.chart.application.trader;

import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/12/09
 */
@Component
public class SimulateUpbitTrader implements Trader {

    private static Map<UpbitAccountRequest, AccountResponses> accounts = new HashMap<>();

    static {
        accounts.put(UpbitAccountRequest.of("million"), AccountResponses.of(UpbitAccount.of("KRW", 1000000.0, 0.0)));
    }

    @Override
    public AccountResponses getAccounts(AccountRequest request) {
        return accounts.get(request);
    }

}
