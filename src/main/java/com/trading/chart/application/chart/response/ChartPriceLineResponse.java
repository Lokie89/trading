package com.trading.chart.application.chart.response;

import com.trading.chart.domain.chart.ChartPriceLine;

/**
 * @author SeongRok.Oh
 * @since 2022/03/03
 */
public interface ChartPriceLineResponse {
    ChartPriceLine toEntity();
}
