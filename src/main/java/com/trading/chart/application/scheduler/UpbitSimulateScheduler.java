package com.trading.chart.application.scheduler;

import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.Simulator;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/04/09
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitSimulateScheduler {

    private final Simulator upbitSimulator;

    @Transactional
    @Scheduled(cron = "0 */3 0-2,4-23 * * *")
    public void carry() {
        List<UpbitSimulation> waitingList = upbitSimulator.getSimulationByStatus(SimulateStatus.READY);
        waitingList.forEach(simulation -> {
            OrderResponses orderBook = simulating(simulation);
            simulation.addOrderBook(orderBook);
            simulation.nextStep();
            log.debug("SIMULATION : SIMULATING");
        });
    }

    private OrderResponses simulating(UpbitSimulation simulation) {
        simulation.nextStep();
        log.debug("SIMULATION : DONE");
        return upbitSimulator.simulate(simulation.toRequest());
    }

    private void alarm() {

    }

}
