package com.trading.chart.domain.simulation;

import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.simulator.response.SimulatedOrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2022/04/13
 */
@EqualsAndHashCode(of = {"side", "market", "orderTime"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class SimulatedOrder {
    @Enumerated(EnumType.STRING)
    private TradeType side;
    private Double price;
    private String market;
    private Double volume;
    private LocalDateTime orderTime;

    public SimulatedOrderResponse toResponse() {
        return SimulatedOrderResponse.builder()
                .side(side)
                .price(price)
                .market(market)
                .volume(volume)
                .orderTime(orderTime)
                .build();
    }

}
