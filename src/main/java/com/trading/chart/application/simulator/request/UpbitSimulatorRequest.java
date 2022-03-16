package com.trading.chart.application.simulator.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.User;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import com.trading.chart.domain.user.response.UpbitUserResponse;
import com.trading.chart.domain.user.response.UserResponse;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */
public class UpbitSimulatorRequest implements SimulatorRequest {

    private final UpbitUser user;
    private final LocalDate start;
    private final LocalDate end;
    private final Integer seed;
    private final Integer cashAtOnce;
    @Getter
    private final List<TradeResourceResponse> tradeResources;

    private UpbitSimulatorRequest(LocalDate start, LocalDate end,
                                  UpbitUser user, Integer seed, Integer cashAtOnce,
                                  List<TradeResourceResponse> tradeResources) {
        this.start = start;
        this.end = end;
        this.user = user;
        this.seed = seed;
        this.cashAtOnce = cashAtOnce;
        this.tradeResources = tradeResources;
    }

    @Override
    public UserResponse toUserResponse() {
        return UpbitUserResponse.builder()
                .upbitClient(user.getUpbitClient())
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

    @Override
    public UpbitSimulation toEntity(SimulateStatus status) {
        return UpbitSimulation.builder()
                .user(user)
                .start(start)
                .end(end)
                .tradeResources(tradeResources.stream().map(TradeResourceResponse::toEntity).collect(Collectors.toSet()))
                .requestDate(LocalDateTime.now())
                .cashAtOnce(cashAtOnce)
                .seed(seed)
                .status(status)
                .build();
    }

    @Override
    public Set<UpbitUnit> mandatoryUnits() {
        return tradeResources.stream().map(TradeResourceResponse::getUnit).collect(Collectors.toSet());
    }

    public static Builder builder(LocalDate start, LocalDate end) {
        return new Builder(start, end);
    }

    public static class Builder {
        private final LocalDate start;
        private final LocalDate end;
        private UpbitUser user;
        private Integer seed = 1000000;
        private Integer cashAtOnce = 50000;
        private List<TradeResourceResponse> tradeResources;

        private Builder(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = Objects.isNull(end) || end.isAfter(LocalDate.now().minusDays(1)) ? LocalDate.now().minusDays(1) : end;
        }

        public Builder client(UpbitUser user) {
            if (Objects.nonNull(user)) {
                this.user = user;
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

        public Builder tradeResources(List<TradeResourceResponse> tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = tradeResources;
            }
            return this;
        }

        public Builder tradeResources(TradeResourceResponse... tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = Arrays.asList(tradeResources);
            }
            return this;
        }

        public SimulatorRequest build() {
            return new UpbitSimulatorRequest(start, end, user, seed, cashAtOnce, tradeResources);

        }

    }

}
