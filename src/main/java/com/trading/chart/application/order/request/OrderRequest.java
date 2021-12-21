package com.trading.chart.application.order.request;

import com.trading.chart.application.trader.request.AccountRequest;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
public interface OrderRequest {
    String getClient();
    AccountRequest toAccountRequest();
}
