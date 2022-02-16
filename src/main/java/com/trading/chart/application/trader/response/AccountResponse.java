package com.trading.chart.application.trader.response;

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
    int toKrw();
    void log();
}
