package com.trading.chart.application.simulator;

import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.UpbitUser;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
public interface Simulator {
    List<UpbitSimulation> getSimulationByStatus(SimulateStatus wait);
    OrderResponses simulate(SimulatorRequest request);
    List<SimulatedOrderResponse> getReport(UpbitUser upbitUser);
}
