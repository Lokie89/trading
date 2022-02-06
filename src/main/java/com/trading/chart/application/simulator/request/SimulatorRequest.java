package com.trading.chart.application.simulator.request;

import com.trading.chart.domain.user.response.UserResponse;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
public interface SimulatorRequest {
    UserResponse toUserResponse();
    LocalDateTime getStart();
    LocalDateTime getEnd();
}
