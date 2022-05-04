package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/12/05
 */
@Getter
@ToString
public class UpbitSimulatorChartRequest extends UpbitChartRequest {
    private final Long upbitSimulationId;

    private UpbitSimulatorChartRequest(String market, UpbitUnit unit, int count, LocalDateTime to, Long upbitSimulationId) {
        super(market, unit, count, to, null);
        this.upbitSimulationId = upbitSimulationId;
    }

    public static Builder builder(String market, UpbitUnit unit, Long upbitSimulationId) {
        return new Builder(market, unit, upbitSimulationId);
    }

    public static class Builder {

        private final String market;
        private final UpbitUnit unit;
        private final Long upbitSimulationId;
        private int count = 1;
        private LocalDateTime to = LocalDateTime.now();


        private Builder(String market, UpbitUnit unit, Long upbitSimulationId) {
            this.upbitSimulationId = upbitSimulationId;
            this.market = market;
            this.unit = unit;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder to(LocalDateTime to) {
            if (Objects.nonNull(to)) {
                this.to = to;
            }
            return this;
        }

        public UpbitSimulatorChartRequest build() {
            return new UpbitSimulatorChartRequest(market, unit, count, to, upbitSimulationId);
        }
    }

    @Override
    public ChartResponse[] forWorkIndex() {
        return fromTo((long) unit.getMinute() * (count));
    }

    @Override
    public int getMandatoryCount() {
        return count;
    }

    @Override
    public int forWorkCount() {
        return count;
    }

    public UpbitSimulatorChartRequest toSimulateRequest() {
        return UpbitSimulatorChartRequest.builder(market, unit, upbitSimulationId)
                .to(to)
                .count(count + 240)
                .build();
    }

    @Override
    public List<ChartRequest> toMessageRequest() {
        List<ChartRequest> requestList = new ArrayList<>();
        int count = this.count;
        do {
            if (count < COUNT_LIMIT) {
                requestList.add(UpbitSimulatorChartRequest.builder(market, unit, upbitSimulationId).to(to).count(count).build());
                break;
            }
            count -= COUNT_LIMIT;
            requestList.add(UpbitSimulatorChartRequest.builder(market, unit, upbitSimulationId).to(goBack(count)).count(COUNT_LIMIT).build());
        } while (count > 0);

        return requestList;
    }
}
