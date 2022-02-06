package com.trading.chart.application.simulator;

import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
public interface Simulator {
    OrderResponses simulate(SimulatorRequest request);
}
