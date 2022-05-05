package com.trading.chart.infra.simulation.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.match.request.TradeStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2022/05/05
 */
@AllArgsConstructor
@Getter
public class SimulateRequest {
    private String start;
    private String end;
    private TradeStrategy buyStrategy;
    private TradeStrategy sellStrategy;
    private UpbitUnit buyUnit;
    private UpbitUnit sellUnit;
    private Integer cashAtOnce;
    private Integer seed;
}
