package com.trading.chart.domain.simulation;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.domain.user.TradeResource;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

}
