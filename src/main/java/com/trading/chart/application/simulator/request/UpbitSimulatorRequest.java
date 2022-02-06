package com.trading.chart.application.simulator.request;

import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;
import com.trading.chart.domain.user.response.UpbitUserResponse;
import com.trading.chart.domain.user.response.UserResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
@Builder
public class UpbitSimulatorRequest implements SimulatorRequest {

    private final LocalDate start;
    private final LocalDate end;
    private final Integer seed;
    private final Integer cashAtOnce;
    private final List<UpbitTradeResourceResponse> tradeResources;

    @Override
    public UserResponse toUserResponse() {
        return UpbitUserResponse.builder()
                .cashAtOnce(cashAtOnce)
                .accounts(AccountResponses.of(UpbitAccount.of("KRW", seed.doubleValue(), (double) 0)))
                .tradeResources(tradeResources)
                .build();
    }

    @Override
    public LocalDateTime getStart() {
        return LocalDateTime.of(start, LocalTime.MIN);
    }

    @Override
    public LocalDateTime getEnd() {
        return LocalDateTime.of(end, LocalTime.MAX);
    }

}
