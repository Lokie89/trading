package com.trading.chart.repository.chart;

import com.trading.chart.application.chart.response.ChartPriceLineResponse;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/03/03
 */
@RequiredArgsConstructor
@Repository
public class UpbitChartBatchRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UpbitChartRepository repository;

    public void saveAll(ChartResponses charts) {
        List<ChartResponse> updateCharts = charts.stream().filter(ChartResponse::isCreated).collect(Collectors.toList());
        final String updateSql
                = "UPDATE upbit_chart set low_price = :lowPrice, opening_price = :openingPrice, trade_price = :tradePrice," +
                " high_price = :highPrice, volume = :volume, change_price = :changePrice, change_rate = :changeRate," +
                " upper_bollinger_band = :upperBollingerBand, down_bollinger_band = :downBollingerBand, rsi = :rsi, " +
                "rsi_signal = :rsiSignal where id = :id";
        namedParameterJdbcTemplate.batchUpdate(updateSql, SqlParameterSourceUtils.createBatch(updateCharts));

        List<Set<ChartPriceLineResponse>> priceLines = updateCharts.stream()
                .map(chart -> chart.getPriceLines().stream()
                        .map(priceLine -> priceLine.toDto(chart.getId()))
                        .collect(Collectors.toSet()))
                .collect(Collectors.toList()
                );
        for (Set<ChartPriceLineResponse> priceLine : priceLines) {
            final String updatePriceLineSql
                    = "UPDATE upbit_chart_price_line set value = :value where upbit_chart_id = :upbitChartId and period = :period";
            namedParameterJdbcTemplate.batchUpdate(updatePriceLineSql, SqlParameterSourceUtils.createBatch(priceLine));
        }
        List<ChartResponse> createCharts = charts.stream().filter(chart -> !chart.isCreated()).collect(Collectors.toList());
        repository.saveAll(createCharts.stream().map(ChartResponse::toEntity).collect(Collectors.toList()));
    }

}
