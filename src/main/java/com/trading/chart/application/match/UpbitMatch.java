package com.trading.chart.application.match;

import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.match.request.MatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
@RequiredArgsConstructor
@Component
public class UpbitMatch implements Match {

    private final Chart cacheUpbitChart;

    @Override
    public boolean match(List<MatchRequest> request) {
        return !request.isEmpty() && request.stream().allMatch(matchRequest -> {
            ChartRequest chartRequest = matchRequest.toChartRequest();
            return matchRequest.test(cacheUpbitChart.getChart(chartRequest));
        });
    }
}
