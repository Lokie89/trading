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

/**
 * @author SeongRok.Oh
 * @since 2021-11-18
 */
@Getter
@AllArgsConstructor
public abstract class UpbitChartRequest implements ChartRequest {
    protected final String market;
    protected final UpbitUnit unit;
    protected final int count;
    protected final LocalDateTime to;
    protected final LinePeriod period;

    @Override
    public ChartKey getRequestKey() {
        return new UpbitKeyPoint(market, unit);
    }

    @Override
    public ChartResponse[] forRequestIndex() {
        return fromTo((long) unit.getMinute() * count);
    }

    protected ChartResponse[] fromTo(long minusMinute) {
        ChartResponse[] chartResponses = new ChartResponse[2];
        LocalDateTime from = this.to.minusMinutes(minusMinute);
        LocalDateTime to = this.to.plusMinutes(1L).withSecond(0);
        chartResponses[0] = new UpbitChartResponse(market, from, unit);
        chartResponses[1] = new UpbitChartResponse(market, to, unit);
        return chartResponses;
    }

    @Override
    public CandleRequest toCandleRequest() {
        return UpbitCandleRequest.builder(unit, market).count(count + period.getPeriod() - 1).lastTime(to).build();
    }

    public int getPeriod() {
        return period.getPeriod();
    }

    @Override
    public ChartResponse[] forWorkIndex() {
        return fromTo((long) unit.getMinute() * (count + period.getPeriod()));
    }

    @Override
    public int getMandatoryCount() {
        return period.getPeriod() + count - 1;
    }
}
