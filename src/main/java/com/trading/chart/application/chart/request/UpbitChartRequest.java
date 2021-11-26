package com.trading.chart.application.chart.request;

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

}
