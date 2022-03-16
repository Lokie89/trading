package com.trading.chart.infra;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SeongRok.Oh
 * @since 2022/03/17
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class TempController {
    private final Chart cacheUpbitChart;

    @GetMapping("/archive")
    public void archive() {
        cacheUpbitChart.archive();
    }

    @GetMapping("/test")
    public ChartResponses get(@RequestParam String market, @RequestParam UpbitUnit unit,@RequestParam Integer count) {
        ChartResponses chartResponses = cacheUpbitChart.getChart(SimpleUpbitChartRequest.builder(market, unit).count(count).build());
        log.info("######### {}", chartResponses.size());
        return chartResponses;
    }

}
