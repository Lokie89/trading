package com.trading.chart.infra.simulation;

import com.trading.chart.application.message.Messenger;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.domain.user.ExchangePlatform;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import com.trading.chart.infra.simulation.request.SimulateRequest;
import com.trading.chart.repository.user.UpbitUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author SeongRok.Oh
 * @since 2022/03/17
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SimulatorController {

    private final Messenger upbitSimulatorMessenger;
    private final UpbitUserRepository upbitUserRepository;

    @GetMapping("/simulate")
    public void simulate(@Valid SimulateRequest simulateRequest) {
        SimulatorRequest request = UpbitSimulatorRequest.builder(
                        LocalDate.parse(simulateRequest.getStart(), DateTimeFormatter.ISO_DATE), LocalDate.parse(simulateRequest.getEnd(), DateTimeFormatter.ISO_DATE)
                )
                .tradeResources(
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.BUY, simulateRequest.getBuyStrategy(), simulateRequest.getBuyUnit()).build(),
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.SELL, simulateRequest.getBuyStrategy(), simulateRequest.getSellUnit()).build()
                ).client(upbitUserRepository.findByUpbitClient("tjdfhrdk10@naver.com").orElse(null))
                .cashAtOnce(simulateRequest.getCashAtOnce())
                .seed(simulateRequest.getSeed())
                .build();
        upbitSimulatorMessenger.send(request);
    }

}
