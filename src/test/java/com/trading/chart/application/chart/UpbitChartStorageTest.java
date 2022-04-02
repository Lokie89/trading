package com.trading.chart.application.chart;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.chart.response.ChartResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2022/02/20
 */
@DisplayName("업비트 차트 저장소 테스트")
@SpringBootTest
public class UpbitChartStorageTest {

    @Autowired
    ChartStorage upbitChartStorage;

    @Autowired
    Chart cacheUpbitChart;

    @Autowired
    ChartIndicator upbitChartIndicator;

    @Transactional
    @DisplayName("업비트 차트 저장 테스트")
    @Test
    void saveUpbitChart() throws InterruptedException {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder(market, unit)
                .count(50)
                .build();
        Thread.sleep(1000);
        cacheUpbitChart.caching(chartRequest);
        ChartResponses charts = cacheUpbitChart.getChart(chartRequest);
        upbitChartStorage.saveChart(charts);
        assertEquals(50, charts.size());
    }


    @Transactional
    @DisplayName("저장된 차트 가져오기 테스트")
    @Test
    void getUpbitDBChart() throws InterruptedException {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder(market, unit)
                .to(LocalDateTime.now())
                .count(3)
                .build();
        Thread.sleep(1000);
        cacheUpbitChart.caching(chartRequest);
        cacheUpbitChart.archive();
        ChartResponses chartResponses = upbitChartStorage.getCharts(chartRequest);
        assertEquals(3, chartResponses.size());
    }

    @DisplayName("차트 요청")
    @Test
    void getUpbitChart() throws InterruptedException {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder(market, unit)
                .to(LocalDateTime.now())
                .count(3)
                .build();
        Thread.sleep(1000);
        cacheUpbitChart.caching(chartRequest);

        ChartResponses chartResponses = cacheUpbitChart.getChart(chartRequest);
        assertEquals(3, chartResponses.size());
    }

    @Transactional
    @DisplayName("볼린저 밴드 차트 요청")
    @Test
    void getBollingerBandsUpbitChart() throws InterruptedException {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.MINUTE_FIVE;
        ChartRequest chartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                .lastTime(LocalDateTime.now().minusDays(3))
                .count(3)
                .build();
        Thread.sleep(1000);
        cacheUpbitChart.caching(chartRequest);
        upbitChartIndicator.drawPriceLine(chartRequest);
        upbitChartIndicator.drawBollingerBands(chartRequest);

        ChartResponses chartResponses = cacheUpbitChart.getChart(chartRequest);
        upbitChartStorage.saveChart(chartResponses);
        assertEquals(3, chartResponses.size());
    }

    @Transactional
    @DisplayName("차트 그려 저장")
    @Test
    void drawLineSaveChart() throws InterruptedException {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        final LinePeriod period = LinePeriod.TWENTY;

        ChartRequest drawLineChartRequest = DrawLineUpbitChartRequest.builder(market, period, unit)
                .lastTime(LocalDateTime.now().minusDays(24))
                .count(5)
                .build();
        Thread.sleep(1000);
        cacheUpbitChart.caching(drawLineChartRequest);

        upbitChartIndicator.drawPriceLine(drawLineChartRequest);
        cacheUpbitChart.archive();

        ChartRequest drawLineChartRequest2 = DrawLineUpbitChartRequest.builder(market, period, unit)
                .lastTime(LocalDateTime.now().minusDays(25))
                .count(5)
                .build();
        cacheUpbitChart.caching(drawLineChartRequest2);

        upbitChartIndicator.drawPriceLine(drawLineChartRequest2);
        cacheUpbitChart.archive();
    }

    @Transactional
    @DisplayName("차트 그려 저장2")
    @Test
    void drawChartAndSave() throws InterruptedException {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        final LinePeriod period = LinePeriod.TWENTY;

        ChartRequest drawLineChartRequest = DrawLineUpbitChartRequest.builder(market, period, unit)
                .lastTime(LocalDateTime.now().minusDays(24))
                .count(5)
                .build();
        Thread.sleep(1000);
        cacheUpbitChart.caching(drawLineChartRequest);
        upbitChartIndicator.drawPriceLine(drawLineChartRequest);
        cacheUpbitChart.archive();

        ChartRequest drawBollingerChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                .lastTime(LocalDateTime.now().minusDays(24))
                .count(5)
                .build();
        cacheUpbitChart.caching(drawLineChartRequest);
        upbitChartIndicator.drawBollingerBands(drawBollingerChartRequest);
        cacheUpbitChart.archive();

    }

}
