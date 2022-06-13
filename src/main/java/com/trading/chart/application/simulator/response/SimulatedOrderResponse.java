package com.trading.chart.application.simulator.response;

import com.trading.chart.application.order.request.TradeType;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2022/04/21
 */
@EqualsAndHashCode(of = {"market"})
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimulatedOrderResponse {
    private TradeType side;
    private Double price;
    private String market;
    private Double volume;
    private LocalDateTime orderTime;
}
