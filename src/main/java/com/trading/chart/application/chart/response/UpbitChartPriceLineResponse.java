package com.trading.chart.application.chart.response;

import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.domain.chart.ChartPriceLine;
import lombok.Builder;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2022/03/03
 */
@Getter
@Builder
public class UpbitChartPriceLineResponse implements ChartPriceLineResponse {
    private Long upbitChartId;
    private String period;
    private double value;

    @Override
    public ChartPriceLine toEntity() {
        return ChartPriceLine.of(LinePeriod.valueOf(period), value);
    }
}
