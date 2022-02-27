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
    void saveUpbitChart() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder(market, unit)
                .count(50)
                .build();
        ChartResponses charts = cacheUpbitChart.getChart(chartRequest);
        ChartResponses chartResponses = upbitChartStorage.saveChart(charts);
        assertEquals(50, chartResponses.size());
    }


    @DisplayName("저장된 차트 가져오기 테스트")
    @Test
    void getUpbitDBChart() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder(market, unit)
                .to(LocalDateTime.now())
                .count(3)
                .build();
        ChartResponses chartResponses = upbitChartStorage.getCharts(chartRequest);
        assertEquals(3, chartResponses.size());
    }

    @DisplayName("차트 요청")
    @Test
    void getUpbitChart() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder(market, unit)
                .to(LocalDateTime.now())
                .count(3)
                .build();

        ChartResponses chartResponses = cacheUpbitChart.getChart(chartRequest);
        assertEquals(3, chartResponses.size());
    }

    @DisplayName("볼린저 밴드 차트 요청")
    @Test
    void getBollingerBandsUpbitChart() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.MINUTE_FIVE;
        ChartRequest chartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                .lastTime(LocalDateTime.now().minusDays(3))
                .count(3)
                .build();
        upbitChartIndicator.drawPriceLine(chartRequest);
        upbitChartIndicator.drawBollingerBands(chartRequest);

        ChartResponses chartResponses = cacheUpbitChart.getChart(chartRequest);
        ChartResponses chartResponses2 = upbitChartStorage.saveChart(chartResponses);
        assertEquals(3, chartResponses.size());
    }

    @DisplayName("차트 그려 저장")
    @Test
    void drawLineSaveChart() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        final LinePeriod period = LinePeriod.TWENTY;

        ChartRequest drawLineChartRequest = DrawLineUpbitChartRequest.builder(market, period, unit)
                .lastTime(LocalDateTime.now().minusDays(24))
                .count(5)
                .build();

        upbitChartIndicator.drawPriceLine(drawLineChartRequest);

        ChartResponses chartResponses = cacheUpbitChart.getChart(drawLineChartRequest);
        upbitChartStorage.saveChart(chartResponses);

        ChartRequest drawLineChartRequest2 = DrawLineUpbitChartRequest.builder(market, period, unit)
                .lastTime(LocalDateTime.now().minusDays(25))
                .count(5)
                .build();

        upbitChartIndicator.drawPriceLine(drawLineChartRequest2);

        ChartResponses chartResponses2 = cacheUpbitChart.getChart(drawLineChartRequest2);
        upbitChartStorage.saveChart(chartResponses2);
    }

}
