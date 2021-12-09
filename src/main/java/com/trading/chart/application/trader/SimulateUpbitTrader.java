package com.trading.chart.application.trader;

import com.trading.chart.application.trader.request.DealtRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.DealtResponse;
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

    private static Map<String, List<AccountResponse>> accounts = new HashMap<>();

    static {
        accounts.put("tjdfhrdk10@naver.com", List.of(UpbitAccount.of("KRW", 1000000.0)));
    }

    @Override
    public List<AccountResponse> getAccounts(String client) {
        return accounts.get(client);
    }

    @Override
    public List<DealtResponse> getRecentlyDealt(DealtRequest dealtRequest) {
        return null;
    }
}
