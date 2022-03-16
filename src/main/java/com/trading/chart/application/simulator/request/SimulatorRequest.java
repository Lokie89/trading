package com.trading.chart.application.simulator.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.response.UserResponse;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
public interface SimulatorRequest {
    UserResponse toUserResponse();
    LocalDateTime getStart();
    LocalDateTime getEnd();
    UpbitSimulation toEntity(SimulateStatus status);
    Set<UpbitUnit> mandatoryUnits();
}
