package com.trading.chart.application.trader.response;

import com.trading.chart.application.chart.response.ChartResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface AccountResponse {
    Double getBalance();
    Double getAvgBuyPrice();
    String getCurrency();
    boolean isOwn();
    void sellBalance(Double balance);
    void buyBalance(Double balance);
    int toKrw(List<ChartResponse> charts);
    void log();
}
