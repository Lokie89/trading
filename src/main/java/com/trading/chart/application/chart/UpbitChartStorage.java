package com.trading.chart.application.chart;

import com.trading.chart.application.candle.Candle;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.repository.chart.ChartRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2022/02/19
 */
@RequiredArgsConstructor
@Component
public class UpbitChartStorage implements ChartStorage {
    private final ChartRepositorySupport customRepository;
    private final Candle upbitCandle;

    @Override
    public ChartResponses getCharts(ChartRequest request) {
        ChartResponses charts = customRepository.getChart(request);
        if (Objects.isNull(charts) || !charts.isSatisfied(request)) {
            return upbitCandle.getCandles(request.toCandleRequest()).toChart();
        }
        return charts;
    }
}
