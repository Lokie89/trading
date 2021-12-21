package com.trading.chart.application.trader;

import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface Trader {
    AccountResponses getAccounts(AccountRequest request);
}
