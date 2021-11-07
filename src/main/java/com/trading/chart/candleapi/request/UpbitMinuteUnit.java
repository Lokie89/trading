package com.trading.chart.candleapi.request;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public enum UpbitMinuteUnit {
    ONE(1),
    THREE(3),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    THIRTY(30),
    SIXTY(60),
    TWOFORTY(240),
    ;

    @Getter
    private int unit;

    UpbitMinuteUnit(int unit) {
        this.unit = unit;
    }

}
