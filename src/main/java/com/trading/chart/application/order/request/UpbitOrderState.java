package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/11/12
 */
@Getter
public enum UpbitOrderState implements OrderState {
    WAIT("wait"),
    WATCH("watch"),
    DONE("done"),
    CANCEL("cancel"),
    ;
    private String state;

    UpbitOrderState(String state) {
        this.state = state;
    }

    @JsonCreator
    public static UpbitOrderState fromString(String state) {
        return Arrays.stream(values())
                .filter(tradeType -> tradeType.getState().equals(state))
                .findAny()
                .orElseThrow()
                ;
    }
}
