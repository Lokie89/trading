package com.trading.chart.application.batch;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.ChartIndicator;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.message.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitChartSubscribeBatch {

    private final MessageQueue messageQueue;
    private final Chart cacheUpbitChart;
    private final TradeItem upbitTradeItem;
    private final ChartIndicator upbitChartIndicator;
    private final Executor exchangeExecutor;


    @Scheduled(cron = "0/1 * * * * *")
    public void callUpbitApiRequest() {
        final MessageClassification messageClassification = MessageClassification.UPBIT_QUOTATION_API;
        final Queue<MessageRequest> callUpbitCandleQueue = messageQueue.subscribe(MessageKey.of(messageClassification));
        final int limit = messageClassification.getRequestCountPerSecond();
        for (int i = 0; i < limit && callUpbitCandleQueue.peek() != null; i++) {
            MessageRequest request = callUpbitCandleQueue.poll();
            if (!callUpbitApiOperate(request)) {
                i--;
            }
        }
    }

    private boolean callUpbitApiOperate(MessageRequest request) {
        final MessageClassification messageClassification = MessageClassification.THREAD;
        MessageType requestType = request.getRequestType();
        if (MessageType.CALL_API_CHART.equals(requestType)) {
            ChartRequest chartRequest = ((UpbitChartRequest) request.getRequest());
            log.info("CALL_API_CHART : {}", chartRequest);
            boolean useAPI = cacheUpbitChart.caching(chartRequest);
            messageQueue.publish(
                    MessageKey.of(messageClassification),
                    MessageRequest.builder().requestType(MessageType.DRAW_CHART).request(chartRequest).build()
            );
            return useAPI;
        }
        if (MessageType.CALL_API_MARKET.equals(requestType)) {
            log.info("CALL_API_MARKET");
            upbitTradeItem.update();
            return true;
        }
        return true;
    }

    @Scheduled(cron = "0/1 * * * * *")
    public void runWithThread() {
        final MessageClassification messageClassification = MessageClassification.THREAD;
        final Queue<MessageRequest> threadQueue = messageQueue.subscribe(MessageKey.of(messageClassification));
        while (Objects.nonNull(threadQueue) && !threadQueue.isEmpty()) {
            MessageRequest request = threadQueue.poll();
            exchangeExecutor.execute(() -> asyncOperate(request));
        }
    }

    private void asyncOperate(MessageRequest request) {
        if (MessageType.DRAW_CHART.equals(request.getRequestType())) {
            final ChartRequest chartRequest = (ChartRequest) request.getRequest();
            final String market = chartRequest.getMarket();
            final LocalDateTime lastTime = chartRequest.getTime();
            final UpbitUnit unit = chartRequest.getUnit();
            final int count = chartRequest.getCount();
            log.info("DRAW_CHART : {}, {}, {}, {}", market, lastTime, unit, count);
            ChartRequest drawLine240 = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWOFORTY, unit).lastTime(lastTime).count(count).build();
            ChartRequest drawLine120 = DrawLineUpbitChartRequest.builder(market, LinePeriod.ONETWENTY, unit).lastTime(lastTime).count(count).build();
            ChartRequest drawLine60 = DrawLineUpbitChartRequest.builder(market, LinePeriod.SIXTY, unit).lastTime(lastTime).count(count).build();
            ChartRequest drawLine20 = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).lastTime(lastTime).count(count).build();
            ChartRequest drawLine5 = DrawLineUpbitChartRequest.builder(market, LinePeriod.FIVE, unit).lastTime(lastTime).count(count).build();
            upbitChartIndicator.drawPriceLine(drawLine240);
            upbitChartIndicator.drawPriceLine(drawLine120);
            upbitChartIndicator.drawPriceLine(drawLine60);
            upbitChartIndicator.drawPriceLine(drawLine20);
            upbitChartIndicator.drawPriceLine(drawLine5);
            ChartRequest drawBollinger = DrawBollingerBandsUpbitChartRequest.builder(market, unit).lastTime(lastTime).count(count).build();
            upbitChartIndicator.drawBollingerBands(drawBollinger);
            ChartRequest drawRsi = DrawRsiUpbitChartRequest.builder(market, unit).lastTime(lastTime).count(count).build();
            upbitChartIndicator.drawRsi(drawRsi);
        }
    }
}
