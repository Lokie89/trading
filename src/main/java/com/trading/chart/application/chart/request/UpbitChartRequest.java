package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitCandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import com.trading.chart.common.ChartKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021-11-18
 */
@ToString
@Getter
@AllArgsConstructor
public abstract class UpbitChartRequest implements ChartRequest {
    protected final String market;
    protected final UpbitUnit unit;
    protected final int count;
    protected final LocalDateTime to;
    protected final LinePeriod period;

    protected final static int COUNT_LIMIT = 200;

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
        LocalDateTime from = this.to.minusMinutes(minusMinute).withSecond(1);
        if (unit.isNotMinuteUnit()) {
            from = from.withHour(9);
        }
        LocalDateTime to = this.to.plusMinutes(1L).withSecond(0);
        if (unit.isNotMinuteUnit()) {
            to = to.withHour(9).minusMinutes(1);
        }
        chartResponses[0] = new UpbitChartResponse(market, from, unit);
        chartResponses[1] = new UpbitChartResponse(market, to, unit);
        return chartResponses;
    }

    @Override
    public LocalDateTime getTime() {
        return to;
    }

    @Override
    public CandleRequest toCandleRequest() {
        return UpbitCandleRequest.builder(unit, market).count(count + (period == null ? 0 : period.getPeriod() - 1)).to(to).build();
    }

    public int getPeriod() {
        return period.getPeriod();
    }

    @Override
    public ChartResponse[] forWorkIndex() {
        return fromTo((long) unit.getMinute() * (count + period.getPeriod()));
    }

    @Override
    public int forWorkCount() {
        return count + period.getPeriod() - 1;
    }

    @Override
    public int getMandatoryCount() {
        return period.getPeriod() + count - 1;
    }


    @Override
    public List<ChartRequest> toMessageRequest() {
        List<ChartRequest> requestList = new ArrayList<>();
        int count = this.count;
        do {
            if (count < COUNT_LIMIT) {
                requestList.add(SimpleUpbitChartRequest.builder(market, unit).to(to).count(count).build());
                break;
            }
            count -= COUNT_LIMIT;
            requestList.add(SimpleUpbitChartRequest.builder(market, unit).to(goBack(count)).count(COUNT_LIMIT).build());
        } while (count > 0);

        return requestList;
    }

    protected LocalDateTime goBack(int count) {
        return to.minusMinutes((long) unit.getMinute() * count);
    }
}
