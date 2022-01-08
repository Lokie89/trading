package com.trading.chart.application.chart;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.chart.response.ChartResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SeongRok.Oh
 * @since 2021-11-18
 */
@DisplayName("차트화 테스트")
@SpringBootTest
public class UpbitChartTest {

    @Autowired
    Chart upbitChart;

    @DisplayName("라인 생성")
    @Test
    void drawLineTest() {
        final String market = "KRW-BTC";
        final LinePeriod period = LinePeriod.get(5).orElseThrow(RuntimeException::new);
        final UpbitUnit unit = UpbitUnit.DAY;
        final int count = 10;
        final LocalDateTime lastTime = LocalDateTime.of(2021, 11, 23, 22, 5);
        ChartRequest drawLineRequest = DrawLineUpbitChartRequest.builder(market, period, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawPriceLine(drawLineRequest);
        ChartResponses chartResponses = upbitChart.getChart(drawLineRequest);
        assertEquals(count, chartResponses.size());
        assertTrue(lastTime.isAfter(chartResponses.getLast().getTime()));
    }

    @DisplayName("볼린저 밴드 생성")
    @Test
    void drawBollingerBandsTest() {
        final String market = "KRW-SSX";
        final LinePeriod period = LinePeriod.get(20).orElseThrow(RuntimeException::new);
        final UpbitUnit unit = UpbitUnit.DAY;
        final int count = 10;
        final LocalDateTime lastTime = LocalDateTime.of(2021, 11, 23, 22, 5);
        ChartRequest drawLineRequest = DrawLineUpbitChartRequest.builder(market, period, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawPriceLine(drawLineRequest);
        ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
        ChartResponses chartResponses = upbitChart.getChart(drawBollingerBandsUpbitChartRequest);
        assertEquals(count, chartResponses.size());
        assertTrue(lastTime.isAfter(chartResponses.getLast().getTime()));
    }

    @DisplayName("RSI 지표 생성")
    @Test
    void drawRsiTest() {
        final String market = "KRW-SSX";
        final UpbitUnit unit = UpbitUnit.DAY;
        final int count = 3;
        final LocalDateTime lastTime = LocalDateTime.of(2021, 11, 23, 22, 5);
        ChartRequest drawRsiUpbitChartRequest = DrawRsiUpbitChartRequest.builder(market, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawRsi(drawRsiUpbitChartRequest);
        ChartResponses chartResponses = upbitChart.getChart(drawRsiUpbitChartRequest);
        assertEquals(count, chartResponses.size());
        assertTrue(lastTime.isAfter(chartResponses.getLast().getTime()));
    }
}
