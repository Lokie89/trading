package com.trading.chart.application.scheduler;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.item.UpbitTradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/04/03
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitPostConstruct {

    private final UpbitTradeItem upbitTradeItem;
    private final Chart cacheUpbitChart;
    // 2000 을 초과하지못함
    private static final int maxCount = 240;

    @PostConstruct
    public void setUp() {
        try {
            baseChart(UpbitUnit.MINUTE_ONE);
            baseChart(UpbitUnit.MINUTE_THREE);
            baseChart(UpbitUnit.MINUTE_FIVE);
            baseChart(UpbitUnit.MINUTE_FIFTEEN);
            baseChart(UpbitUnit.MINUTE_THIRTY);
            baseChart(UpbitUnit.MINUTE_SIXTY);
            baseChart(UpbitUnit.MINUTE_TWOFORTY);
            baseChart(UpbitUnit.DAY);
            baseChart(UpbitUnit.WEEK);
            baseChart(UpbitUnit.MONTH);
        } catch (InterruptedException e) {
            log.warn("Thread Error {}", e.getMessage());
        }
    }

    private void baseChart(UpbitUnit unit) throws InterruptedException {
        final List<ItemResponse> items = upbitTradeItem.getKrwItems();
        final int sleepTime = ((maxCount / 200) + 1) * 100;
        final LocalDateTime now = LocalDateTime.now();

        for (ItemResponse item : items) {
            Thread.sleep(sleepTime);
            final String market = item.getName();
            ChartRequest request = SimpleUpbitChartRequest.builder(market, unit).count(maxCount).to(now).build();
            cacheUpbitChart.caching(request);
        }
    }
}
