package com.trading.chart.application.chart;

import com.trading.chart.application.candle.Candle;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.chart.response.ChartPriceLine;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.common.CacheChart;
import com.trading.chart.common.ChartKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@RequiredArgsConstructor
@Component
public class CacheUpbitChart implements Chart {

    private final Candle apiUpbitCandle;
    public final CacheChart cache = new CacheChart();

    private void verifyExistCache(ChartRequest chartRequest) {
        // 실제 데이터 검증 ( 키 값에 따른 데이터가 있는지, 그 데이터에 lastTime 의 데이터가 있는지, 그 전 데이터가 count 개수 만큼 있는지 )
        // 데이터가 없으면 (나중에는 DB를 한번 조회 후 뒤에 일들을 DBUpbitChart 한테 시킬거임) apiUpbitCandle getCandles 호출 하여 cache 등록
        ChartKey chartKey = chartRequest.getRequestKey();
        ChartResponses charts = cache.get(chartKey);
        if (Objects.isNull(charts)) {
            cache.put(chartKey, getChartWithApi(chartRequest));
            return;
        }
        final int mandatoryCount = chartRequest.getPeriod() + chartRequest.getCount() - 1;
        ChartResponse[] fromTo = chartRequest.forWorkIndex();
        if (charts.substitute(fromTo[0], fromTo[1]).size() < mandatoryCount) {
            cache.get(chartKey).add(getChartWithApi(chartRequest));
        }
    }

    private ChartResponses getChartWithApi(ChartRequest chartRequest) {
        return apiUpbitCandle.getCandles(chartRequest.getCandleRequest()).toChart();
    }

    @Override
    public void drawLine(ChartRequest chartRequest) {
        verifyExistCache(chartRequest);

        ChartResponses charts = cache.get(chartRequest.getRequestKey());
        if (Objects.nonNull(charts)) {
            ChartResponse[] fromTo = chartRequest.forWorkIndex();
            ChartResponses forWork = charts.substitute(fromTo[0], fromTo[1]);
            final Queue<ChartResponse> queue = new LinkedList<>();
            int period = chartRequest.getPeriod();
            Spliterator<ChartResponse> spliterator = forWork.spliterator();
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
    }

    @Override
    public ChartResponses getChart(ChartRequest chartRequest) {
        verifyExistCache(chartRequest);
        ChartResponses charts = cache.get(chartRequest.getRequestKey());
        ChartResponse[] fromTo = chartRequest.forRequestIndex();
        return charts.substitute(fromTo[0], fromTo[1]);
    }

}
