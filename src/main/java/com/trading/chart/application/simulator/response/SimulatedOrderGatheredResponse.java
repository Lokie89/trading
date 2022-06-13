package com.trading.chart.application.simulator.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2022/04/21
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimulatedOrderGatheredResponse {
    private String market;
    private Integer gathered;
}
