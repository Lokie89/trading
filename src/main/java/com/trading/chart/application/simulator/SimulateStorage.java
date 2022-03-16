package com.trading.chart.application.simulator;

import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.UpbitUser;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/04/13
 */
public interface SimulateStorage {
    void initiate(SimulatorRequest request);
    List<UpbitSimulation> getSimulationByStatus(SimulateStatus status);
    List<SimulatedOrderResponse> getReport(UpbitUser upbitUser);
}
