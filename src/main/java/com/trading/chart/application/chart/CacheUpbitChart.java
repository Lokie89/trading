package com.trading.chart.application.chart;

import com.trading.chart.application.candle.Candle;
import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.UpbitKeyPoint;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.common.CacheChart;
import com.trading.chart.common.ChartKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@RequiredArgsConstructor
@Component
public class CacheUpbitChart implements Chart {

    private final CacheChart cache = new CacheChart();
    private final ChartStorage upbitChartStorage;
    private final Candle upbitCandle;
    private final TradeItem upbitTradeItem;

    // TODO : API 사용에 대한 횟수 return 인데 이름이 마음에 안듦
    @Override
    public int caching(ChartRequest request) {
        // 실제 데이터 검증 ( 키 값에 따른 데이터가 있는지, 그 데이터에 lastTime 의 데이터가 있는지, 그 전 데이터가 count 개수 만큼 있는지 )
        ChartKey chartKey = request.getRequestKey();
        ChartResponses charts = cache.get(chartKey);
        if (Objects.isNull(charts)) {
            cache.add(chartKey, ChartResponses.of(new ArrayList<>()));
            charts = cache.get(chartKey);
        }
        if (charts.isNotSatisfied(request)) {
            charts.addAll(upbitChartStorage.getCharts(request));
        }
        // 여기서 제거하면 아이디 값이 없어지기 때문에 (저장할때 duplicate) 맨 뒤에걸 업데이트 쳐야됨

        if (charts.isNotSatisfied(request)) {
            List<CandleRequest> candleRequestList = request.toCandleRequest();
            ChartResponses apiCharts = upbitCandle.getCandles(candleRequestList).toChart(request.getUnit());
            apiCharts = apiCharts.substitute(request);
            charts.updateLast(apiCharts);
            charts.addAll(apiCharts);
            return candleRequestList.size();
        }
        return 0;
    }

    @Override
    public ChartResponses getWorkChart(ChartRequest request) {
//        verifyCache(request);
        ChartResponses charts = cache.get(request.getRequestKey());
        if (Objects.nonNull(charts)) {
            ChartResponse[] fromTo = request.forWorkIndex();
            return charts.substitute(fromTo[0], fromTo[1]);
        }
        // TODO : Custom Exception
        throw new RuntimeException();
    }

    @Override
    public ChartResponses getChart(ChartRequest request) {
//        verifyCache(request);
        ChartResponses charts = cache.get(request.getRequestKey());
        if (Objects.isNull(charts)) {
            return ChartResponses.of(new ArrayList<>());
        }
        ChartResponse[] fromTo = request.forRequestIndex();
        return charts.substitute(fromTo[0], fromTo[1]);
    }

    @Override
    public void archive() {
        for (ChartKey key : cache.keySet()) {
            ChartResponses archivingCharts = cache.get(key);
            upbitChartStorage.saveChart(archivingCharts);
            cache.remove(key);
        }
    }

    @Override
    public List<ChartResponse> recent(LocalDateTime to) {
        List<ItemResponse> items = upbitTradeItem.getKrwItems();
        List<ChartResponse> chartResponses = new ArrayList<>();
        for (ItemResponse item : items) {
            final String market = item.getName();
            final UpbitUnit unit = UpbitUnit.MINUTE_ONE;
            ChartKey key = UpbitKeyPoint.builder().market(market).unit(unit).build();
            ChartResponses charts = cache.get(key);
            if (Objects.nonNull(charts) && charts.isNotEmpty()) {
                chartResponses.add(charts.substituteTo(UpbitChartResponse.builder().market(market).unit(unit).time(to).build()).getLast());
            }
        }
        return chartResponses;
    }

}
