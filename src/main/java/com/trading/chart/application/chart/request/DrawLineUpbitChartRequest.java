package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021-11-18
 */
public class DrawLineUpbitChartRequest extends UpbitChartRequest {

    private DrawLineUpbitChartRequest(String market, UpbitUnit unit, Integer count, LocalDateTime to, LinePeriod period) {
        super(market, unit, count, to, period);
    }

    public static Builder builder(final String market, final LinePeriod period, final UpbitUnit unit) {
        return new Builder(market, period, unit);
    }

    public static class Builder {
        private final String market;
        private final UpbitUnit unit;
        private int count = 100;
        private LocalDateTime to = LocalDateTime.now();
        private final LinePeriod period;

        public Builder(final String market, final LinePeriod period, final UpbitUnit unit) {
            this.market = market;
            this.period = period;
            this.unit = unit;
        }

        public Builder count(final int count) {
            this.count = count;
            return this;
        }

        public Builder to(LocalDateTime to) {
            this.to = Objects.nonNull(to) ? to : LocalDateTime.now();
            return this;
        }

        public DrawLineUpbitChartRequest build() {
            return new DrawLineUpbitChartRequest(this.market, this.unit, this.count, this.to, this.period);
        }
    }
}
