package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/12/05
 */
public class SimpleUpbitChartRequest extends UpbitChartRequest {
    private SimpleUpbitChartRequest(String market, UpbitUnit unit, int count, LocalDateTime to) {
        super(market, unit, count, to, null);
    }

    public static Builder builder(String market, UpbitUnit unit) {
        return new Builder(market, unit);
    }

    public static class Builder {

        private final String market;
        private final UpbitUnit unit;
        private int count;
        private LocalDateTime to;


        private Builder(String market, UpbitUnit unit) {
            this.market = market;
            this.unit = unit;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder to(LocalDateTime to) {
            this.to = to;
            return this;
        }

        public SimpleUpbitChartRequest build() {
            return new SimpleUpbitChartRequest(market, unit, count, to);
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
}
