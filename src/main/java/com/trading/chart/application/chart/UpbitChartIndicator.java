package com.trading.chart.application.chart;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.domain.chart.ChartPriceLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;

/**
 * @author SeongRok.Oh
 * @since 2022/02/27
 */

@RequiredArgsConstructor
@Component
public class UpbitChartIndicator implements ChartIndicator {

    private final Chart cacheUpbitChart;

    private Spliterator<ChartResponse> getChartCanvas(ChartRequest request) {
        ChartResponses chartResponses = cacheUpbitChart.getWorkChart(request);
        if (chartResponses.isNotEmpty()) {
            return chartResponses.spliterator();
        }
        return null;
    }

    @Override
    public void drawPriceLine(ChartRequest request) {
        final Queue<ChartResponse> queue = new LinkedList<>();
        int period = request.getPeriod();
        Spliterator<ChartResponse> spliterator = getChartCanvas(request);
        if (Objects.isNull(spliterator)) {
            return;
        }
        while (spliterator.tryAdvance(
                (chart) -> {
                    queue.add(chart);
                    if (queue.size() == period) {
                        chart.drawPriceLine(
                                ChartPriceLine.of(LinePeriod.get(period).orElseThrow(),
                                        queue.stream().mapToDouble(ChartResponse::getTradePrice).average().orElse(0)));
                        queue.poll();
                    }
                }
        )) ;
    }

    @Override
    public void drawBollingerBands(ChartRequest request) {
        final Queue<ChartResponse> queue = new LinkedList<>();
        int period = request.getPeriod();
        Spliterator<ChartResponse> spliterator = getChartCanvas(request);
        if (Objects.isNull(spliterator)) {
            return;
        }
        while (spliterator.tryAdvance(
                (chart) -> {
                    queue.add(chart);
                    if (queue.size() == period) {
                        double avg = queue.stream().mapToDouble(ChartResponse::getTradePrice).average().orElse(0);
                        double deviationSum = queue.stream().mapToDouble(chartResponse -> Math.pow(avg - chartResponse.getTradePrice(), 2)).sum();
                        double standardDeviation = Math.sqrt(deviationSum / period);
                        chart.drawBollingerBands(standardDeviation);
                        queue.poll();
                    }
                }
        )) ;
    }

    @Override
    public void drawRsi(ChartRequest request) {
        final Queue<ChartResponse> queue = new LinkedList<>();
        int period = request.getPeriod();
        Spliterator<ChartResponse> spliterator = getChartCanvas(request);
        if (Objects.isNull(spliterator)) {
            return;
        }
        while (spliterator.tryAdvance(
                (chart) -> {
                    queue.add(chart);
                    if (queue.size() == period + 1) {
                        ChartResponse poll = queue.poll();
                        double beforeTradePrice = poll.getTradePrice();
                        double ups = 0;
                        double downs = 0;
                        for (int i = 0; i < queue.size(); i++) {
                            ChartResponse tempPoll = queue.poll();
                            double tradePrice = tempPoll.getTradePrice();
                            if (tradePrice >= beforeTradePrice) {
                                ups += (tradePrice - beforeTradePrice);
                            } else {
                                downs += (beforeTradePrice - tradePrice);
                            }
                            beforeTradePrice = tradePrice;
                            queue.add(tempPoll);
                        }
                        double au = ups / period;
                        double ad = downs / period;
                        double rsi = ad == 0 ? 1 : (au / ad) / (1 + (au / ad)) * 100;
                        chart.drawRsi(rsi);
                    }
                }
        )) ;
    }
}
