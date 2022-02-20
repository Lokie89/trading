package com.trading.chart.domain.chart;

import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.chart.response.ChartPriceLine;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author SeongRok.Oh
 * @since 2022/02/17
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UpbitChartPriceLine {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "upbitChart_id")
    @ManyToOne
    private UpbitChart upbitChart;
    @Enumerated(EnumType.STRING)
    private LinePeriod period;
    private double value;

    public ChartPriceLine toDto() {
        return ChartPriceLine.builder()
                .period(period)
                .value(value)
                .build();
    }
}
