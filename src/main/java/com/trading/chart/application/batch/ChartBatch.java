package com.trading.chart.application.batch;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.ChartIndicator;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/02/27
 */

// TODO : Spring Batch 로 변경
@Slf4j
@RequiredArgsConstructor
@Component
public class ChartBatch {

    private final Chart cacheUpbitChart;
    private final ChartIndicator upbitChartIndicator;
    private final TradeItem upbitTradeItem;

    private final static int REQUEST_COUNT = 2;

    private List<ItemResponse> items;

    @PostConstruct
    void setUp() {
        updateItems();
        final int dayToMinutes = 1440;
        operate(UpbitUnit.MINUTE_ONE, dayToMinutes / UpbitUnit.MINUTE_ONE.getMinute());
        operate(UpbitUnit.MINUTE_THREE, dayToMinutes / UpbitUnit.MINUTE_THREE.getMinute());
        operate(UpbitUnit.MINUTE_FIVE, dayToMinutes / UpbitUnit.MINUTE_FIVE.getMinute());
        operate(UpbitUnit.MINUTE_TEN, dayToMinutes / UpbitUnit.MINUTE_TEN.getMinute());
        operate(UpbitUnit.MINUTE_FIFTEEN, dayToMinutes / UpbitUnit.MINUTE_FIFTEEN.getMinute());
        operate(UpbitUnit.MINUTE_THIRTY, dayToMinutes / UpbitUnit.MINUTE_THIRTY.getMinute());
        operate(UpbitUnit.MINUTE_SIXTY, dayToMinutes / UpbitUnit.MINUTE_SIXTY.getMinute());
        operate(UpbitUnit.MINUTE_TWOFORTY, dayToMinutes / UpbitUnit.MINUTE_TWOFORTY.getMinute());
        operate(UpbitUnit.DAY, 7);
        operate(UpbitUnit.WEEK, 7);
        operate(UpbitUnit.MONTH, 7);
    }

    private void updateItems() {
        items = upbitTradeItem.getItems()
                .stream()
                .filter(ItemResponse::isKrwMarket)
                .collect(Collectors.toList())
        ;
    }

    private void operate(final UpbitUnit unit, final int count) {
        for (ItemResponse item : items) {
            final String market = item.getName();
            log.info("operate {}", market);
            ChartRequest drawLine5 = DrawLineUpbitChartRequest.builder(market, LinePeriod.FIVE, unit).count(count).build();
            ChartRequest drawLine20 = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).count(count).build();
            ChartRequest drawLine60 = DrawLineUpbitChartRequest.builder(market, LinePeriod.SIXTY, unit).count(count).build();
            ChartRequest drawLine120 = DrawLineUpbitChartRequest.builder(market, LinePeriod.ONETWENTY, unit).count(count).build();
            ChartRequest drawLine240 = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWOFORTY, unit).count(count).build();
            upbitChartIndicator.drawPriceLine(drawLine5);
            upbitChartIndicator.drawPriceLine(drawLine20);
            upbitChartIndicator.drawPriceLine(drawLine60);
            upbitChartIndicator.drawPriceLine(drawLine120);
            upbitChartIndicator.drawPriceLine(drawLine240);
            ChartRequest drawBollinger = DrawBollingerBandsUpbitChartRequest.builder(market, unit).count(count).build();
            upbitChartIndicator.drawBollingerBands(drawBollinger);
            ChartRequest drawRsi = DrawRsiUpbitChartRequest.builder(market, unit).count(count).build();
            upbitChartIndicator.drawRsi(drawRsi);
        }
    }

    @Scheduled(cron = "1 0 9 * * *")
    public void updateMarket() {
        updateItems();
    }

    @Scheduled(cron = "1 0/1 * * * *")
    public void operateChartOneMinute() {
        log.info("operate {} minutes", 1);
        final UpbitUnit unit = UpbitUnit.MINUTE_ONE;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0/3 * * * *")
    public void operateChartThreeMinute() {
        log.info("operate {} minutes", 3);
        final UpbitUnit unit = UpbitUnit.MINUTE_THREE;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0/5 * * * *")
    public void operateChartFiveMinute() {
        log.info("operate {} minutes", 5);
        final UpbitUnit unit = UpbitUnit.MINUTE_FIVE;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0/10 * * * *")
    public void operateChartTenMinute() {
        log.info("operate {} minutes", 10);
        final UpbitUnit unit = UpbitUnit.MINUTE_TEN;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0/15 * * * *")
    public void operateChartFifteenMinute() {
        log.info("operate {} minutes", 15);
        final UpbitUnit unit = UpbitUnit.MINUTE_FIFTEEN;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0/30 * * * *")
    public void operateChartThirtyMinute() {
        log.info("operate {} minutes", 30);
        final UpbitUnit unit = UpbitUnit.MINUTE_THIRTY;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0 0/1 * * *")
    public void operateChartSixtyMinute() {
        log.info("operate {} minutes", 60);
        final UpbitUnit unit = UpbitUnit.MINUTE_SIXTY;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0 0/4 * * *")
    public void operateChartTwoFortyMinute() {
        log.info("operate {} minutes", 240);
        final UpbitUnit unit = UpbitUnit.MINUTE_TWOFORTY;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0 9 * * *")
    public void operateChartDay() {
        log.info("operate 1 day");
        final UpbitUnit unit = UpbitUnit.DAY;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0 9 * * *")
    public void operateChartWeek() {
        log.info("operate 1 week");
        final UpbitUnit unit = UpbitUnit.WEEK;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "1 0 9 * * *")
    public void operateChartMonth() {
        log.info("operate 1 month");
        final UpbitUnit unit = UpbitUnit.MONTH;
        operate(unit, REQUEST_COUNT);
    }

    @Scheduled(cron = "50 0 9 * * *")
    public void archiveChart() {
        cacheUpbitChart.archive();
    }
}
