package com.trading.chart.application.simulator;

import com.trading.chart.application.exchange.Exchange;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */

@RequiredArgsConstructor
@Component
public class UpbitSimulator implements Simulator {

    private final Exchange simulateUpbitExchange;
    private final SimulateStorage upbitSimulateStorage;

    @Override
    public List<UpbitSimulation> getSimulationByStatus(SimulateStatus status) {
        return upbitSimulateStorage.getSimulationByStatus(status);
    }

    @Override
    public OrderResponses simulate(SimulatorRequest request) {
        return repeatSimulate(request.getStart(), request.getEnd(), request.toUserResponse());
    }

    @Override
    public List<SimulatedOrderResponse> getReport(UpbitUser upbitUser) {
        return upbitSimulateStorage.getReport(upbitUser);
    }

    private OrderResponses repeatSimulate(LocalDateTime start, LocalDateTime end, UserResponse user) {
        OrderResponses orderResponses = OrderResponses.of(new ArrayList<>());
        Integer stepMinutes = user.minimumOfTradeResource();
        for (LocalDateTime simulatingDateTime = start;
             simulatingDateTime.isBefore(end);
             simulatingDateTime = simulatingDateTime.plusMinutes(stepMinutes)) {
            orderResponses.addAll(simulateUpbitExchange.exchange(user, simulatingDateTime));
        }
        return orderResponses;
    }

}
