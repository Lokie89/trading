package com.trading.chart.application.chart;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2022/04/04
 */
@DisplayName("차트 캐싱 테스트")
@SpringBootTest
public class UpbitChartCachingTest {

    @Autowired
    Chart cacheUpbitChart;

    @DisplayName("캐싱 로직 테스트")
    @Test
    void cachingTest() {
        ChartRequest chartRequest = SimpleUpbitChartRequest.builder("KRW-LTC", UpbitUnit.DAY).count(201).build();
        cacheUpbitChart.caching(chartRequest);
    }
}
