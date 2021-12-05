package com.trading.chart.application.match.request;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
public interface MatchRequest {
    ChartRequest toChartRequest();
    boolean test(ChartResponses chart);
}
