package com.trading.chart.application.scheduler;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.chart.request.UpbitSimulatorChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.message.MessageType;
import com.trading.chart.application.message.Messenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.SortedSet;

/**
 * @author SeongRok.Oh
 * @since 2022/02/27
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitChartPublishScheduler {

    private final Chart cacheUpbitChart;
    private final TradeItem upbitTradeItem;
    private final UpbitPostConstruct postConstruct;
    private final Messenger upbitChartMessenger;
    private final Messenger upbitMarketMessenger;
    private final Messenger upbitSimulatorChartMessenger;

    private void updateItems() {
        log.info("publish : {}", MessageType.CALL_API_MARKET);
        upbitMarketMessenger.send(null);
    }

    public void operate(LocalDateTime start, LocalDateTime end, UpbitUnit unit, Long upbitSimulationId) {
        final SortedSet<ItemResponse> items = upbitTradeItem.getKrwItems();
        final int count = (int) (Duration.between(start, end).getSeconds() / 60 / unit.getMinute()) + 1;
        for (ItemResponse item : items) {
            final String market = item.getName();
            upbitSimulatorChartMessenger.send(UpbitSimulatorChartRequest.builder(market, unit, upbitSimulationId).to(end).count(count + 240).build());
        }
    }

    private void operateDepends(final UpbitUnit unit) {
        final int unitMinute = unit.getMinute();
        final SortedSet<ItemResponse> items = upbitTradeItem.getKrwItems();
        for (ItemResponse item : items) {
            final String market = item.getName();
            int count = 0;
            ChartResponses charts = cacheUpbitChart.getChart(SimpleUpbitChartRequest.builder(market, unit).count(1).build());
            if (charts.isNotEmpty()) {
                count = (int) (Duration.between(charts.getLast().getTime().withSecond(0), LocalDateTime.now().withSecond(0)).getSeconds() / 60 / unitMinute) + 1;
            }
            if (count < 2) {
                count = 2;
            }
            upbitChartMessenger.send(SimpleUpbitChartRequest.builder(market, unit).to(LocalDateTime.now()).count(count).build());
        }
    }

    @Scheduled(cron = "1 0 9 * * *")
    public void updateMarket() {
        updateItems();
    }

    @Scheduled(cron = "1 0/1 0-2,4-23 * * *")
    public void operateChartOneMinute() {
        log.info("operate {} minutes", 1);
        final UpbitUnit unit = UpbitUnit.MINUTE_ONE;
        operateDepends(unit);
    }

    //    @Scheduled(cron = "1 0/3 * * * *")
//    public void operateChartThreeMinute() {
//        log.info("operate {} minutes", 3);
//        final UpbitUnit unit = UpbitUnit.MINUTE_THREE;
//        operateDepends(unit, REQUEST_COUNT);
//    }
//
    @Scheduled(cron = "1 0/5 0-2,4-23 * * *")
    public void operateChartFiveMinute() {
        log.info("operate {} minutes", 5);
        final UpbitUnit unit = UpbitUnit.MINUTE_FIVE;
        operateDepends(unit);
    }

    //
//    @Scheduled(cron = "1 0/10 * * * *")
//    public void operateChartTenMinute() {
//        log.info("operate {} minutes", 10);
//        final UpbitUnit unit = UpbitUnit.MINUTE_TEN;
//        operateDepends(unit, REQUEST_COUNT);
//    }
//
    @Scheduled(cron = "1 0/15 0-2,4-23 * * *")
    public void operateChartFifteenMinute() {
        log.info("operate {} minutes", 15);
        final UpbitUnit unit = UpbitUnit.MINUTE_FIFTEEN;
        operateDepends(unit);
    }

    @Scheduled(cron = "1 0/30 0-2,4-23 * * *")
    public void operateChartThirtyMinute() {
        log.info("operate {} minutes", 30);
        final UpbitUnit unit = UpbitUnit.MINUTE_THIRTY;
        operateDepends(unit);
    }

    @Scheduled(cron = "1 0 0-2,4-23 * * *")
    public void operateChartSixtyMinute() {
        log.info("operate {} minutes", 60);
        final UpbitUnit unit = UpbitUnit.MINUTE_SIXTY;
        operateDepends(unit);
    }

    //
    @Scheduled(cron = "1 0 0/4 * * *")
    public void operateChartTwoFortyMinute() {
        log.info("operate {} minutes", 240);
        final UpbitUnit unit = UpbitUnit.MINUTE_TWOFORTY;
        operateDepends(unit);
    }

    //
    @Scheduled(cron = "1 0/1 0-2,4-23 * * *")
    public void operateChartDay() {
        log.info("operate 1 day");
        final UpbitUnit unit = UpbitUnit.DAY;
        operateDepends(unit);
    }

    //
    @Scheduled(cron = "1 0 9 * * *")
    public void operateChartWeek() {
        log.info("operate 1 week");
        final UpbitUnit unit = UpbitUnit.WEEK;
        operateDepends(unit);
    }

    //
    @Scheduled(cron = "1 0 9 * * *")
    public void operateChartMonth() {
        log.info("operate 1 month");
        final UpbitUnit unit = UpbitUnit.MONTH;
        operateDepends(unit);
    }

    @Scheduled(cron = "0 5 3 * * *")
    public void archiveChart() {
        cacheUpbitChart.archive();
        postConstruct.setUp();
        log.error("ARCHIVE AND RELOAD!!");
    }
}
