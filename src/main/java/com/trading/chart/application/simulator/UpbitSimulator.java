package com.trading.chart.application.simulator;

import com.trading.chart.application.exchange.Exchange;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.domain.user.response.UserResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */

@RequiredArgsConstructor
@Component
public class UpbitSimulator implements Simulator {

    private final Exchange simulateUpbitExchange;
    private final UpbitUserRepository userRepository;

    // TODO 전날까지만 하는걸로

    @Override
    public OrderResponses simulate(SimulatorRequest request) {
        return repeatSimulate(request.getStart(), request.getEnd(), request.toUserResponse());
    }

    private OrderResponses repeatSimulate(LocalDateTime start, LocalDateTime end, UserResponse user) {
        OrderResponses orderResponses = OrderResponses.of(Collections.emptyList());
        for (LocalDateTime simulatingDateTime = start;
             simulatingDateTime.isBefore(end);
             simulatingDateTime = simulatingDateTime.plusMinutes(1)) {
            orderResponses.addAll(simulateUpbitExchange.exchange(user, simulatingDateTime));
        }
        return orderResponses;
    }

}
