package com.trading.chart.application.simulator;

import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.SimulatedOrder;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.repository.simulation.UpbitSimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/04/13
 */
@RequiredArgsConstructor
@Component
public class UpbitSimulateStorage implements SimulateStorage {

    private final UpbitSimulationRepository repository;

    @Override
    public UpbitSimulation initiate(SimulatorRequest request) {
        return repository.save(request.toEntity(SimulateStatus.WAIT));
    }

    @Override
    public List<UpbitSimulation> getSimulationByStatus(SimulateStatus status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public UpbitSimulation getSimulationById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<SimulatedOrderResponse> getReport(UpbitUser upbitUser) {
        return repository.findFirstByUserOrderByRequestDateDesc(upbitUser).getOrderBook().stream()
                .map(SimulatedOrder::toDto)
                .collect(Collectors.toList())
                ;
    }
}
