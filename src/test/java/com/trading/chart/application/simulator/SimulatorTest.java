package com.trading.chart.application.simulator;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.DrawBollingerBandsUpbitChartRequest;
import com.trading.chart.application.chart.request.DrawLineUpbitChartRequest;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */

@DisplayName("시뮬레이터 테스트")
@SpringBootTest
public class SimulatorTest {

    @Autowired
    Simulator upbitSimulator;

    @Autowired
    TradeItem upbitTradeItem;

    @Autowired
    Chart upbitChart;

    @Autowired
    Trader simulateUpbitTrader;

    final LocalDate start = LocalDate.of(2022, 2, 2);
    final LocalDate end = LocalDate.of(2022, 2, 5);

    @BeforeEach
    void setUp() {
        long seconds = Duration.between(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX)).getSeconds();
        long minutes = seconds / 60;
        final int oneMinutesCount = (int) (minutes / UpbitUnit.MINUTE_ONE.getMinute());
        drawLineBollingerRsi(UpbitUnit.MINUTE_ONE, LocalDateTime.of(end, LocalTime.MAX), oneMinutesCount);
        final int threeMinutesCount = (int) (minutes / UpbitUnit.MINUTE_THREE.getMinute());
        drawLineBollingerRsi(UpbitUnit.MINUTE_THREE, LocalDateTime.of(end, LocalTime.MAX), threeMinutesCount);
    }

    void drawLineBollingerRsi(UpbitUnit unit, LocalDateTime date, Integer count) {
        List<ItemResponse> items = upbitTradeItem.getItems().stream()
                .filter(ItemResponse::isKrwMarket)
                .collect(Collectors.toList());
        int i = 0;
        for (ItemResponse item : items) {
            if (i > 0) {
                break;
            }
            final String market = item.getName();
            ChartRequest drawPriceLinesUpbitChartRequest = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).lastTime(date).count(count).build();
            upbitChart.drawPriceLine(drawPriceLinesUpbitChartRequest);
            ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                    .lastTime(date)
                    .count(count)
                    .build();

            upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
            i++;
        }
    }

    @DisplayName("업비트 시뮬레이팅 테스트")
    @Test
    void simulateTest() {

        final Integer seedMoney = 1000000;
        final Integer cashAtOnce = 50000;
        final UpbitTradeResourceResponse buyTradeResource = UpbitTradeResourceResponse.builder(TradeType.BUY, TradeStrategy.LOWER_BOLLINGERBANDS, UpbitUnit.MINUTE_THREE).matchMin(2).matchMax(2).build();
        final UpbitTradeResourceResponse sellTradeResource = UpbitTradeResourceResponse.builder(TradeType.SELL, TradeStrategy.HIGHER_BOLLINGERBANDS, UpbitUnit.MINUTE_THREE).matchMin(2).matchMax(2).build();
        final List<UpbitTradeResourceResponse> tradeResourceList = new ArrayList<>();
        tradeResourceList.add(buyTradeResource);
        tradeResourceList.add(sellTradeResource);
        SimulatorRequest request = UpbitSimulatorRequest.builder(start)
                .end(end)
                .seed(seedMoney)
                .cashAtOnce(cashAtOnce)
                .tradeResources(tradeResourceList)
                .build();

        OrderResponses orderResponses = upbitSimulator.simulate(request);
        orderResponses.log();
        AccountResponses accountResponses = simulateUpbitTrader.getAccounts(null);
        Assertions.assertTrue(orderResponses.size() > 0);
    }
}
