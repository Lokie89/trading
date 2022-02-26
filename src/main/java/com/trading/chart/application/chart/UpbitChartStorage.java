package com.trading.chart.application.chart;

import com.trading.chart.application.candle.Candle;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.domain.chart.UpbitChart;
import com.trading.chart.repository.chart.ChartRepositorySupport;
import com.trading.chart.repository.chart.UpbitChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/02/19
 */
@RequiredArgsConstructor
@Component
public class UpbitChartStorage implements ChartStorage {
    private final UpbitChartRepository repository;
    private final ChartRepositorySupport customRepository;
    private final Candle upbitCandle;

    @Override
    public ChartResponses getCharts(ChartRequest request) {
        ChartResponses charts = customRepository.getChart(request);
        if (Objects.isNull(charts) || charts.isNotSatisfied(request)) {
            ChartResponses apiCharts = upbitCandle.getCandles(request.toCandleRequest()).toChart(request.getUnit());
            apiCharts.add(charts);
            return apiCharts;
        }
        return charts;
    }

    @Override
    public ChartResponses saveChart(ChartResponses responses) {
        List<UpbitChart> charts = repository.saveAll(responses.stream().map(ChartResponse::toEntity).collect(Collectors.toList()));
        return ChartResponses.of(charts.stream().map(UpbitChart::toDto).collect(Collectors.toList()));
    }


}
