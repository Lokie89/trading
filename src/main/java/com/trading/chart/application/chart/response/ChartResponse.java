package com.trading.chart.application.chart.response;

import com.trading.chart.domain.chart.ChartPriceLine;
import com.trading.chart.domain.chart.UpbitChart;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
public interface ChartResponse {
    Long getId();
    Double getLowPrice();
    Double getOpeningPrice();
    Double getTradePrice();
    Double getHighPrice();
    Double getVolume();
    double getDownBollingerBand();
    double getUpperBollingerBand();
    double getRsi();
    double getRsiSignal();
    Double getChangePrice();
    Double getChangeRate();
    LocalDateTime getTime();
    void drawPriceLine(ChartPriceLine line);
    void drawBollingerBands(Double standardDeviation);
    void drawRsi(Double rsi);
    UpbitChart toEntity();
    boolean isCreated();
    Set<ChartPriceLine> getPriceLines();
}
