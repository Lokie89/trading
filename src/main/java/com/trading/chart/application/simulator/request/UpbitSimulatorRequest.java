package com.trading.chart.application.simulator.request;

import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;
import com.trading.chart.domain.user.response.UpbitUserResponse;
import com.trading.chart.domain.user.response.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
public class UpbitSimulatorRequest implements SimulatorRequest {

    private final LocalDate start;
    private final LocalDate end;
    private final Integer seed;
    private final Integer cashAtOnce;
    private final List<UpbitTradeResourceResponse> tradeResources;

    private UpbitSimulatorRequest(LocalDate start, LocalDate end,
                                  Integer seed, Integer cashAtOnce,
                                  List<UpbitTradeResourceResponse> tradeResources) {
        this.start = start;
        this.end = end;
        this.seed = seed;
        this.cashAtOnce = cashAtOnce;
        this.tradeResources = tradeResources;
    }

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

    public static Builder builder(LocalDate start) {
        return new Builder(start);
    }

    public static class Builder {
        private LocalDate start;
        private LocalDate end;
        private Integer seed = 1000000;
        private Integer cashAtOnce = 50000;
        private List<UpbitTradeResourceResponse> tradeResources;

        private Builder(LocalDate start) {
            this.start = start;
        }

        public Builder end(LocalDate end) {
            if (Objects.nonNull(end)) {
                this.end = end.isAfter(LocalDate.now().minusDays(1)) ? LocalDate.now().minusDays(1) : end;
            }
            return this;
        }

        public Builder seed(Integer seed) {
            if (Objects.nonNull(seed)) {
                this.seed = seed;
            }
            return this;
        }

        public Builder cashAtOnce(Integer cashAtOnce) {
            if (Objects.nonNull(cashAtOnce)) {
                this.cashAtOnce = cashAtOnce;
            }
            return this;
        }

        public Builder tradeResources(List<UpbitTradeResourceResponse> tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = tradeResources;
            }
            return this;
        }

        public Builder tradeResources(UpbitTradeResourceResponse... tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = Arrays.asList(tradeResources);
            }
            return this;
        }

        public SimulatorRequest build() {
            return new UpbitSimulatorRequest(start, end, seed, cashAtOnce, tradeResources);

        }

    }

}
