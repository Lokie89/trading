package com.trading.chart.application.scheduler;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.ChartIndicator;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.message.*;
import com.trading.chart.application.simulator.SimulateStorage;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitChartSubscribeScheduler {

    private final UpbitChartPublishScheduler publishScheduler;
    private final MessageQueue messageQueue;
    private final Chart cacheUpbitChart;
    private final TradeItem upbitTradeItem;
    private final ChartIndicator upbitChartIndicator;
    private final Executor exchangeExecutor;
    private final SimulateStorage upbitSimulateStorage;


    @Scheduled(cron = "0/1 * 0-2,3-23 * * *")
    public void callUpbitApiRequest() {
        final MessageClassification messageClassification = MessageClassification.UPBIT_QUOTATION_API;
        final Queue<MessageRequest> callUpbitCandleQueue = messageQueue.subscribe(MessageKey.of(messageClassification));
        final int limit = messageClassification.getRequestCountPerSecond();
        for (int i = 0; i < limit && callUpbitCandleQueue.peek() != null; ) {
            MessageRequest request = callUpbitCandleQueue.poll();
            i += callUpbitApiOperate(request);
        }
    }

    private int callUpbitApiOperate(MessageRequest request) {
        final MessageClassification messageClassification = MessageClassification.THREAD;
        MessageType requestType = request.getRequestType();
        if (MessageType.CALL_API_CHART.equals(requestType)) {
            ChartRequest chartRequest = ((UpbitChartRequest) request.getRequest());
            log.info("CALL_API_CHART : {}", chartRequest);
            int useAPI = cacheUpbitChart.caching(chartRequest);
            messageQueue.publish(
                    MessageKey.of(messageClassification),
                    MessageRequest.builder().requestType(MessageType.DRAW_CHART).request(chartRequest).build()
            );
            return useAPI;
        }
        if (MessageType.SIMULATE_CALL_API_CHART.equals(requestType)) {
            ChartRequest chartRequest = ((UpbitChartRequest) request.getRequest());
            log.info("SIMULATE_CALL_API_CHART : {}", chartRequest);
            int useAPI = cacheUpbitChart.caching(chartRequest.toSimulateRequest());
            messageQueue.publish(
                    MessageKey.of(messageClassification),
                    MessageRequest.builder().requestType(MessageType.DRAW_CHART).request(chartRequest).build()
            );
            return useAPI;
        }
        if (MessageType.CALL_API_MARKET.equals(requestType)) {
            log.info("CALL_API_MARKET");
            upbitTradeItem.update();
        }
        return 1;
    }

    @Scheduled(cron = "0/1 * 0-2,3-23 * * *")
    public void runWithThread() {
        final MessageClassification messageClassification = MessageClassification.THREAD;
        final Queue<MessageRequest> threadQueue = messageQueue.subscribe(MessageKey.of(messageClassification));
        while (Objects.nonNull(threadQueue) && !threadQueue.isEmpty()) {
            MessageRequest request = threadQueue.poll();
            asyncOperate(request);
//            exchangeExecutor.execute(() -> asyncOperate(request));
        }
    }

    private void asyncOperate(MessageRequest request) {
        if (MessageType.DRAW_CHART.equals(request.getRequestType())) {
            final ChartRequest chartRequest = (ChartRequest) request.getRequest();
            final String market = chartRequest.getMarket();
            final LocalDateTime to = chartRequest.getTime();
            final UpbitUnit unit = chartRequest.getUnit();
            final int count = chartRequest.getCount();
            log.info("DRAW_CHART : {}, {}, {}, {}", market, to, unit, count);
            ChartRequest drawLine240 = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWOFORTY, unit).to(to).count(count).build();
            ChartRequest drawLine120 = DrawLineUpbitChartRequest.builder(market, LinePeriod.ONETWENTY, unit).to(to).count(count).build();
            ChartRequest drawLine60 = DrawLineUpbitChartRequest.builder(market, LinePeriod.SIXTY, unit).to(to).count(count).build();
            ChartRequest drawLine20 = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).to(to).count(count).build();
            ChartRequest drawLine5 = DrawLineUpbitChartRequest.builder(market, LinePeriod.FIVE, unit).to(to).count(count).build();
            upbitChartIndicator.drawPriceLine(drawLine240);
            upbitChartIndicator.drawPriceLine(drawLine120);
            upbitChartIndicator.drawPriceLine(drawLine60);
            upbitChartIndicator.drawPriceLine(drawLine20);
            upbitChartIndicator.drawPriceLine(drawLine5);
            ChartRequest drawBollinger = DrawBollingerBandsUpbitChartRequest.builder(market, unit).to(to).count(count).build();
            upbitChartIndicator.drawBollingerBands(drawBollinger);
            ChartRequest drawRsi = DrawRsiUpbitChartRequest.builder(market, unit).to(to).count(count).build();
            upbitChartIndicator.drawRsi(drawRsi);
        }
        if (MessageType.SIMULATE.equals(request.getRequestType())) {
            final SimulatorRequest simulatorRequest = (SimulatorRequest) request.getRequest();
            Set<UpbitUnit> mandatoryUnits = simulatorRequest.mandatoryUnits();
            mandatoryUnits.forEach(unit -> publishScheduler.operate(simulatorRequest.getStart(), simulatorRequest.getEnd(), unit));
            upbitSimulateStorage.initiate(simulatorRequest);
        }
    }
}
