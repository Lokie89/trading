package com.trading.chart.application.chart;

import com.trading.chart.application.chart.request.ChartRequest;

/**
 * @author SeongRok.Oh
 * @since 2022/02/27
 */
public interface ChartIndicator {
    void drawPriceLine(ChartRequest request);
    void drawBollingerBands(ChartRequest request);
    void drawRsi(ChartRequest request);
}
