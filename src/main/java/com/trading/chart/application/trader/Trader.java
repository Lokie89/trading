package com.trading.chart.application.trader;

import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.OrderResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface Trader {
    List<AccountResponse> getAccounts(String id);
    List<OrderResponse> getOrders(String id);
}
