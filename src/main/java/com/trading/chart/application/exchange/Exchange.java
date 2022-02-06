package com.trading.chart.application.exchange;

import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.domain.user.response.UserResponse;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2022/02/05
 */
public interface Exchange {
    OrderResponses exchange(UserResponse user, LocalDateTime date);
}
