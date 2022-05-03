package com.trading.chart.application.chart;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/21
 */
public interface Chart {
    ChartResponses getChart(ChartRequest request);
    ChartResponses getWorkChart(ChartRequest request);
    void archive();
    List<ChartResponse> recent(LocalDateTime to);
    void caching(ChartRequest request);
}
