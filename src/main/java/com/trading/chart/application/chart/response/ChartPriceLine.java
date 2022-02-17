package com.trading.chart.application.chart.response;

import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.domain.chart.UpbitChartPriceLine;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/11/25
 */
@EqualsAndHashCode(of = "period")
@Getter
public class ChartPriceLine {
    private LinePeriod period;
    private double value;

    private ChartPriceLine(LinePeriod period, double value) {
        this.period = period;
        this.value = value;
    }

    public static ChartPriceLine of(LinePeriod period, double value) {
        return new ChartPriceLine(period, value);
    }

    public UpbitChartPriceLine toUpbitChartPriceLine(){
        return UpbitChartPriceLine.builder()
                .period(period)
                .value(value)
                .build();
    }
}
