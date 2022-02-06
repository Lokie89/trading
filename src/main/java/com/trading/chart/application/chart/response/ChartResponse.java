package com.trading.chart.application.chart.response;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
public interface ChartResponse {
    Double getTradePrice();
    double getDownBollingerBand();
    double getUpperBollingerBand();
    double getRsi();
    LocalDateTime getTime();
    void drawPriceLine(ChartPriceLine line);
    void drawBollingerBands(Double standardDeviation);
    void drawRsi(Double rsi);
}
