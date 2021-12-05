package com.trading.chart.application.match;

import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.match.request.MatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
@RequiredArgsConstructor
@Component
public class UpbitMatch implements Match {

    private final Chart upbitChart;

    @Override
    public boolean match(MatchRequest request) {
        ChartRequest chartRequest = request.toChartRequest();
        return request.test(upbitChart.getChart(chartRequest));
    }
}
