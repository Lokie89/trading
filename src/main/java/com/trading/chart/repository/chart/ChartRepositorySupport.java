package com.trading.chart.repository.chart;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;

/**
 * @author SeongRok.Oh
 * @since 2022/02/19
 */
public interface ChartRepositorySupport {
    ChartResponses getChart(ChartRequest request);
}
