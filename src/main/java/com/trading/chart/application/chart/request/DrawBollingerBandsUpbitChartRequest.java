package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitCandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;

import java.time.LocalDateTime;
import java.util.Objects;

public class DrawBollingerBandsUpbitChartRequest extends UpbitChartRequest {

    private final LinePeriod period = LinePeriod.TWENTY;

    private DrawBollingerBandsUpbitChartRequest(String market, UpbitUnit unit, int count, LocalDateTime to) {
        super(market, unit, count, to);
    }

    @Override
    public CandleRequest getCandleRequest() {
        return UpbitCandleRequest.builder(unit, market).count(count + period.getPeriod() - 1).lastTime(to).build();
    }

    @Override
    public int getPeriod() {
        return period.getPeriod();
    }

    @Override
    public ChartResponse[] forWorkIndex() {
        return fromTo((long) unit.getMinute() * (count + period.getPeriod()));
    }

    public static DrawBollingerBandsUpbitChartRequest.Builder builder(final String market, final UpbitUnit unit) {
        return new DrawBollingerBandsUpbitChartRequest.Builder(market, unit);
    }

    public static class Builder {
        private final String market;
        private final UpbitUnit unit;
        private int count = 100;
        private LocalDateTime to = LocalDateTime.now();

        public Builder(final String market, final UpbitUnit unit) {
            this.market = market;
            this.unit = unit;
        }

        public DrawBollingerBandsUpbitChartRequest.Builder count(final int count) {
            this.count = count;
            return this;
        }

        public DrawBollingerBandsUpbitChartRequest.Builder lastTime(LocalDateTime to) {
            this.to = Objects.nonNull(to) ? to : LocalDateTime.now();
            return this;
        }

        public DrawBollingerBandsUpbitChartRequest build() {
            return new DrawBollingerBandsUpbitChartRequest(this.market, this.unit, this.count, this.to);
        }
    }
}
