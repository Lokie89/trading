package com.trading.chart.application.scheduler;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.ChartIndicator;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.message.*;
import com.trading.chart.application.simulator.SimulateStorage;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.domain.simulation.UpbitSimulation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
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
    private final SimulateStorage upbitSimulateStorage;
    private final Messenger upbitDrawChartMessenger;
    private final Messenger upbitChartMessenger;
    private final Messenger upbitSimulatorChartMessenger;
    private final Messenger upbitSimulatorDrawChartMessenger;
    private final Executor exchangeExecutor;


    @Scheduled(cron = "0/1 * 0-2,4-23 * * *")
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
        int useApi = 0;
        MessageType requestType = request.getRequestType();
        if (MessageType.CALL_API_CHART.equals(requestType)) {
            UpbitChartRequest chartRequest = ((UpbitChartRequest) request.getRequest());
            log.info("CALL_API_CHART : {} {} {} {}", chartRequest.getMarket(), chartRequest.getTime(), chartRequest.getUnit(), chartRequest.getCount());
            try {
                useApi = cacheUpbitChart.caching(chartRequest);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
                upbitChartMessenger.send(chartRequest);
            }
            upbitDrawChartMessenger.send(chartRequest);
        }
        if (MessageType.SIMULATE_CALL_API_CHART.equals(requestType)) {
            UpbitSimulatorChartRequest chartRequest = ((UpbitSimulatorChartRequest) request.getRequest());
            log.info("SIMULATE_CALL_API_CHART : {} {} {} {}", chartRequest.getMarket(), chartRequest.getTime(), chartRequest.getUnit(), chartRequest.getCount());
            try {
                useApi = cacheUpbitChart.caching(chartRequest);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
                upbitSimulatorChartMessenger.send(request);
            }
            upbitSimulatorDrawChartMessenger.send(chartRequest);
        }
        if (MessageType.CALL_API_MARKET.equals(requestType)) {
            log.info("CALL_API_MARKET");
            upbitTradeItem.update();
        }
        return useApi;
    }

    @Transactional
    @Scheduled(cron = "0/1 * 0-2,4-23 * * *")
    public void runWithThread() {
        final MessageClassification messageClassification = MessageClassification.THREAD;
        final Queue<MessageRequest> threadQueue = messageQueue.subscribe(MessageKey.of(messageClassification));
        while (Objects.nonNull(threadQueue) && !threadQueue.isEmpty()) {
            MessageRequest request = threadQueue.poll();
//            asyncOperate(request);
            exchangeExecutor.execute(() -> asyncOperate(request));
            if (MessageType.SIMULATE_DRAW_CHART.equals(request.getRequestType())) {
                final UpbitSimulatorChartRequest simulatorChartRequest = (UpbitSimulatorChartRequest) request.getRequest();
                Long simulationId = simulatorChartRequest.getUpbitSimulationId();
                if (upbitTradeItem.getKrwItems().last().getName().equals(simulatorChartRequest.getMarket())) {
                    log.info("SIMULATE : READY");
                    upbitSimulateStorage.getSimulationById(simulationId).ready();
                }
            }
        }
    }

    private void asyncOperate(MessageRequest request) {
        if (MessageType.DRAW_CHART.equals(request.getRequestType())
                || MessageType.SIMULATE_DRAW_CHART.equals(request.getRequestType())) {
            final ChartRequest chartRequest = (ChartRequest) request.getRequest();
            final String market = chartRequest.getMarket();
            final LocalDateTime to = chartRequest.getTime();
            final UpbitUnit unit = chartRequest.getUnit();
            final int count = chartRequest.getCount();
            log.info("DRAW_CHART : {}, {}, {}, {}", market, to, unit, count);
            // 차트 저장 로직 연관 있음
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
            log.info("SIMULATE : INITIATE");
            UpbitSimulation simulation = upbitSimulateStorage.initiate(simulatorRequest);
            mandatoryUnits.forEach(unit -> publishScheduler.operate(simulatorRequest.getStart(), simulatorRequest.getEnd(), unit, simulation.getId()));
        }
    }
}
