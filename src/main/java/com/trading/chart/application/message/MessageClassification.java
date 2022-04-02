package com.trading.chart.application.message;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */
@Getter
public enum MessageClassification {
    THREAD(0),
    UPBIT_QUOTATION_API(10),
    UPBIT_EXCHANGE_API(8),
    ;

    private final int requestCountPerSecond;

    MessageClassification(int requestCountPerSecond){
        this.requestCountPerSecond = requestCountPerSecond;
    }
}
