package com.trading.chart.domain.chart;

import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.chart.response.ChartPriceLineResponse;
import com.trading.chart.application.chart.response.UpbitChartPriceLineResponse;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author SeongRok.Oh
 * @since 2021/11/25
 */
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "period")
@Getter
@NoArgsConstructor
@Embeddable
public class ChartPriceLine {

    @Enumerated(EnumType.STRING)
    private LinePeriod period;
    private double value;

    public static ChartPriceLine of(LinePeriod period, double value) {
        return new ChartPriceLine(period, value);
    }

    public ChartPriceLineResponse toDto(Long chartId){
        return UpbitChartPriceLineResponse.builder()
                .upbitChartId(chartId)
                .period(period.toString())
                .value(value)
                .build();
    }
}
