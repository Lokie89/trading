package com.trading.chart.application.message;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */
@Getter
public enum MessageType {
    CALL_API_CHART,
    CALL_API_MARKET,
    DRAW_CHART,
    ;

}
