package com.trading.chart.application.message;

import com.trading.chart.application.simulator.request.SimulatorRequest;

/**
 * @author SeongRok.Oh
 * @since 2022/04/09
 */
public interface SimulatorMessenger {
    void send(SimulatorRequest request);
}
