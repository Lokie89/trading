package com.trading.chart.application.chart;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/11/21
 */
public interface Chart {
    void drawPriceLine(ChartRequest request);
    void drawBollingerBands(ChartRequest request);
    ChartResponses getChart(ChartRequest request);
    void drawRsi(ChartRequest request);
}
