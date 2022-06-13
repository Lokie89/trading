package com.trading.chart.domain.simulation;

import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import com.trading.chart.domain.user.TradeResource;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.infra.simulation.response.UpbitSimulationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/04/13
 */
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "upbit_simulation")
@Entity
public class UpbitSimulation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "upbit_user_id", nullable = false)
    @ManyToOne
    private UpbitUser user;
    private LocalDateTime requestDate;
    private LocalDate start;
    private LocalDate end;
    private Integer seed;
    private Integer cashAtOnce;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "simulate_trade_resource",
            joinColumns = @JoinColumn(name = "simulation_id"))
    private Set<TradeResource> tradeResources;

    @Enumerated(EnumType.STRING)
    private SimulateStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "simulate_order_book",
            joinColumns = @JoinColumn(name = "simulation_id"))
    @OrderBy("orderTime")
    private Set<SimulatedOrder> orderBook;

    public void nextStep() {
        int ordinal = this.status.ordinal();
        ordinal++;
        this.status = SimulateStatus.values()[ordinal];
    }

    public void ready() {
        this.status = SimulateStatus.READY;
    }

    public SimulatorRequest toRequest() {
        return UpbitSimulatorRequest.builder(start, end)
                .client(user)
                .seed(seed)
                .cashAtOnce(cashAtOnce)
                .tradeResources(tradeResources.stream().map(TradeResource::toDto).collect(Collectors.toList()))
                .build();
    }

    public void addOrderBook(OrderResponses orderResponses) {
        orderBook = new HashSet<>(orderResponses.toEntity());
    }

    public UpbitSimulationResponse toResponse() {
        List<SimulatedOrderResponse> orderBookResponse = orderBook.stream().map(SimulatedOrder::toResponse).collect(Collectors.toList());
        List<SimulatedOrderResponse> sellOrderBooks = orderBookResponse.stream().filter(order -> TradeType.SELL.equals(order.getSide())).collect(Collectors.toList());
        List<SimulatedOrderResponse> buyOrderBooks = orderBookResponse.stream().filter(order -> TradeType.BUY.equals(order.getSide()) && sellOrderBooks.contains(order)).collect(Collectors.toList());



        return UpbitSimulationResponse.builder()
                .id(id)
                .requestDate(requestDate)
                .start(start)
                .end(end)
                .seed(seed)
                .cashAtOnce(cashAtOnce)
                .tradeResources(tradeResources.stream().map(TradeResource::toDto).collect(Collectors.toList()))
                .orderBook(orderBookResponse)
                .build();
    }

}
