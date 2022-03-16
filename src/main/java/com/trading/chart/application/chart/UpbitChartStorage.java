package com.trading.chart.application.chart;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.repository.chart.ChartRepositorySupport;
import com.trading.chart.repository.chart.UpbitChartBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * @author SeongRok.Oh
 * @since 2022/02/19
 */
@RequiredArgsConstructor
@Component
public class UpbitChartStorage implements ChartStorage {
    private final ChartRepositorySupport customRepository;
    private final UpbitChartBatchRepository batchRepository;

    @Override
    public ChartResponses getCharts(ChartRequest request) {
        return customRepository.getChart(request);
    }

    @Transactional
    @Override
    public void saveChart(ChartResponses responses) {
        batchRepository.saveAll(responses);
    }

}
