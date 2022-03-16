package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitCandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/11/27
 */
public class DrawRsiUpbitChartRequest extends UpbitChartRequest {

    private DrawRsiUpbitChartRequest(String market, UpbitUnit unit, int count, LocalDateTime to, LinePeriod period) {
        super(market, unit, count, to, period);
    }

    public static Builder builder(final String market, final UpbitUnit unit) {
        return new Builder(market, unit);
    }

    @Override
    public ChartResponse[] forWorkIndex() {
        return fromTo((long) unit.getMinute() * (count + period.getPeriod() + 1));
    }

    @Override
    public List<CandleRequest> toCandleRequest() {
        return UpbitCandleRequest.builder(unit, market).count(count + period.getPeriod()).to(to).build();
    }

    public static class Builder {
        private final String market;
        private final UpbitUnit unit;
        private int count = 100;
        private LocalDateTime to = LocalDateTime.now();
        private final LinePeriod period = LinePeriod.FOURTEEN;

        public Builder(final String market, final UpbitUnit unit) {
            this.market = market;
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

        public DrawRsiUpbitChartRequest build() {
            return new DrawRsiUpbitChartRequest(this.market, this.unit, this.count, this.to, this.period);
        }
    }
}
