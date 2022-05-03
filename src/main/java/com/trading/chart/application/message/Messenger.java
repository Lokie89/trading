package com.trading.chart.application.message;

/**
 * @author SeongRok.Oh
 * @since 2022/04/09
 */
public interface Messenger {
    void send(Object request);
}
