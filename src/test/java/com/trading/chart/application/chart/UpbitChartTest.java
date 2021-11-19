package com.trading.chart.application.chart;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.DrawLineUpbitChartRequest;
import com.trading.chart.application.chart.request.LinePeriod;
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
        final String market = "KRW-BTT";
        final LinePeriod period = LinePeriod.get(5).orElseThrow(RuntimeException::new);
        final UpbitUnit unit = UpbitUnit.DAY;
        final int count = 10;
        final LocalDateTime lastTime = LocalDateTime.of(2021, 11, 23, 22, 5);
        ChartRequest drawLineRequest = DrawLineUpbitChartRequest.builder(market, period, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawLine(drawLineRequest);
        ChartResponses chartResponses = upbitChart.getChart(drawLineRequest);
        assertEquals(count, chartResponses.size());
        assertTrue(lastTime.isBefore(chartResponses.getLast().getTime()));
    }
}
