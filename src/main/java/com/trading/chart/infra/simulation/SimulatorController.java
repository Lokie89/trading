package com.trading.chart.infra.simulation;

import com.trading.chart.application.message.Messenger;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.simulator.Simulator;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.ExchangePlatform;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import com.trading.chart.infra.simulation.request.SimulateRequest;
import com.trading.chart.infra.simulation.response.UpbitSimulationResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/03/17
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class SimulatorController {

    private final Messenger upbitSimulatorMessenger;
    private final UpbitUserRepository upbitUserRepository;

    private final Simulator upbitSimulator;

    @GetMapping("/simulate")
    public @ResponseBody void simulate(@Valid SimulateRequest simulateRequest) {
        UpbitUser upbitUser = upbitUserRepository.findByUpbitClient("tjdfhrdk10@naver.com").orElse(null);
        SimulatorRequest request = UpbitSimulatorRequest.builder(
                        LocalDate.parse(simulateRequest.getStart(), DateTimeFormatter.ISO_DATE), LocalDate.parse(simulateRequest.getEnd(), DateTimeFormatter.ISO_DATE)
                )
                .tradeResources(
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.BUY, simulateRequest.getBuyStrategy(), simulateRequest.getBuyUnit()).build(),
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.SELL, simulateRequest.getSellStrategy(), simulateRequest.getSellUnit()).build()
                ).client(upbitUser)
                .cashAtOnce(simulateRequest.getCashAtOnce())
                .seed(simulateRequest.getSeed())
                .build();
        upbitSimulatorMessenger.send(request);
    }

    @GetMapping("/simulation")
    public String simulate() {
        return "simulation";
    }

    @GetMapping("/simulated")
    public @ResponseBody
    List<UpbitSimulationResponse> simulated() {
        return upbitSimulator.getSimulationByStatus(SimulateStatus.DONE).stream()
                .map(UpbitSimulation::toResponse)
                .collect(Collectors.toList())
                ;
    }

}
