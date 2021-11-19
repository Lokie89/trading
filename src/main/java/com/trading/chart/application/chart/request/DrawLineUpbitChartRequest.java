package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitCandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import com.trading.chart.common.ChartKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021-11-18
 */
@Getter
@AllArgsConstructor
public class DrawLineUpbitChartRequest implements ChartRequest {
    private final String market;
    private final LinePeriod period;
    private final UpbitUnit unit;
    private final int count;
    private final LocalDateTime to;

    public int getPeriod() {
        return period.getPeriod();
    }

    @Override
    public ChartKey getRequestKey() {
        return new UpbitKeyPoint(market, unit);
    }

    @Override
    public CandleRequest getCandleRequest() {
        return UpbitCandleRequest.builder(unit, market).count(count + period.getPeriod() - 1).lastTime(to).build();
    }

    @Override
    public ChartResponse[] forWorkIndex() {
        return fromTo((long) unit.getMinute() * (count + period.getPeriod()));
    }

    @Override
    public ChartResponse[] forRequestIndex() {
        return fromTo((long) unit.getMinute() * count);
    }

    private ChartResponse[] fromTo(long minusMinute) {
        ChartResponse[] chartResponses = new ChartResponse[2];
        LocalDateTime from = this.to.minusMinutes(minusMinute);
        LocalDateTime to = this.to.plusMinutes(1L).withSecond(0);
        chartResponses[0] = new UpbitChartResponse(market, from, unit);
        chartResponses[1] = new UpbitChartResponse(market, to, unit);
        return chartResponses;
    }

    public static Builder builder(final String market, final LinePeriod period, final UpbitUnit unit) {
        return new Builder(market, period, unit);
    }

    public static class Builder {
        private final String market;
        private final LinePeriod period;
        private final UpbitUnit unit;
        private int count = 100;
        private LocalDateTime to = LocalDateTime.now();

        public Builder(final String market, final LinePeriod period, final UpbitUnit unit) {
            this.market = market;
            this.period = period;
            this.unit = unit;
        }

        public Builder count(final int count) {
            this.count = count;
            return this;
        }

        public Builder lastTime(LocalDateTime to) {
            this.to = Objects.nonNull(to) ? to : LocalDateTime.now();
            return this;
        }

        public DrawLineUpbitChartRequest build() {
            return new DrawLineUpbitChartRequest(this.market, this.period, this.unit, this.count, this.to);
        }
    }
}
