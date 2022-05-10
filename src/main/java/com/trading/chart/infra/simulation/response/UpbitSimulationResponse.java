package com.trading.chart.infra.simulation.response;

import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/05/10
 */
@Builder
@AllArgsConstructor
@Getter
public class UpbitSimulationResponse {
    private Long id;
    private LocalDateTime requestDate;
    private LocalDate start;
    private LocalDate end;
    private Integer seed;
    private Integer cashAtOnce;
    private List<TradeResourceResponse> tradeResources;
    private List<SimulatedOrderResponse> orderBooks;
}
