package com.trading.chart.application.trader;

import com.trading.chart.application.trader.response.AccountResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface Trader {
    List<AccountResponse> getAccounts(String id);
}
