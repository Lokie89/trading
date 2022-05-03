package com.trading.chart.application.scheduler;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.Simulator;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2022/04/09
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitSimulateScheduler {


    private final Simulator upbitSimulator;
    private final Chart cacheUpbitChart;

    @Transactional
    @Scheduled(cron = "0/1 * 0-2,3-23 * * *")
    public void carry() {
        List<UpbitSimulation> waitingList = upbitSimulator.getSimulationByStatus(SimulateStatus.WAIT);
        waitingList.forEach(simulation -> {
            boolean satisfied = checkChartCount(simulation);
            if (satisfied) {
                OrderResponses orderBook = simulating(simulation);
                simulation.addOrderBook(orderBook);
                simulation.nextStep();
            }
        });
    }

    private boolean checkChartCount(UpbitSimulation simulation) {
        LocalDate start = simulation.getStart();
        LocalDate end = simulation.getEnd();
        Set<UpbitUnit> mandatoryUnits = simulation.mandatoryUnits();
        return mandatoryUnits.stream().allMatch(unit -> {
            int count = (int) Duration.between(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX)).getSeconds() / 60 / unit.getMinute();
            int size = cacheUpbitChart.getChart(SimpleUpbitChartRequest.builder("KRW-BTC", unit).to(LocalDateTime.of(simulation.getEnd(), LocalTime.MAX)).count(count).build()).size();
            return count == size;
        });
    }

    private OrderResponses simulating(UpbitSimulation simulation) {
        simulation.nextStep();
        return upbitSimulator.simulate(simulation.toRequest());
    }

    private void alarm() {

    }

}
