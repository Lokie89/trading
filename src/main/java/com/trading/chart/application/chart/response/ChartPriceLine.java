package com.trading.chart.application.chart.response;

import com.trading.chart.application.chart.request.LinePeriod;
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
    private Double value;

    private ChartPriceLine(LinePeriod period, Double value) {
        this.period = period;
        this.value = value;
    }

    public static ChartPriceLine of(LinePeriod period, Double value) {
        return new ChartPriceLine(period, value);
    }
}
