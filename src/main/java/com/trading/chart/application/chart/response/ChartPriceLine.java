package com.trading.chart.application.chart.response;

import com.trading.chart.application.chart.request.LinePeriod;
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
}
