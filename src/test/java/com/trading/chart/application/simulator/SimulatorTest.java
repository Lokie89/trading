package com.trading.chart.application.simulator;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.ChartIndicator;
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
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.ExchangePlatform;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/02/08
 */

@Disabled
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

    @Autowired
    ChartIndicator upbitChartIndicator;

    @Autowired
    UpbitUserRepository userRepository;

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
        List<ItemResponse> items = upbitTradeItem.getKrwItems();
        for (ItemResponse item : items) {
            final String market = item.getName();
            ChartRequest drawPriceLinesUpbitChartRequest = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).to(date).count(count).build();
            upbitChartIndicator.drawPriceLine(drawPriceLinesUpbitChartRequest);
            ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                    .to(date)
                    .count(count)
                    .build();

            upbitChartIndicator.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
        }
    }

    @DisplayName("업비트 시뮬레이팅 테스트")
    @Test
    void simulateTest() {
        final String client = "tjdfhrdk10@naver.com";

        final Integer seedMoney = 1000000;
        final Integer cashAtOnce = 50000;
        final ExchangePlatform platform = ExchangePlatform.UPBIT;
        final TradeResourceResponse buyTradeResource = TradeResourceResponse.builder(platform, TradeType.BUY, TradeStrategy.LOWER_BOLLINGERBANDS, UpbitUnit.MINUTE_THREE).matchMin(2).matchMax(2).build();
        final TradeResourceResponse sellTradeResource = TradeResourceResponse.builder(platform, TradeType.SELL, TradeStrategy.HIGHER_BOLLINGERBANDS, UpbitUnit.MINUTE_THREE).matchMin(2).matchMax(2).build();
        final List<TradeResourceResponse> tradeResourceList = new ArrayList<>();
        tradeResourceList.add(buyTradeResource);
        tradeResourceList.add(sellTradeResource);
        SimulatorRequest request = UpbitSimulatorRequest.builder(start, end)
                .client(userRepository.findByUpbitClient(client).orElseThrow(RuntimeException::new))
                .seed(seedMoney)
                .cashAtOnce(cashAtOnce)
                .tradeResources(tradeResourceList)
                .build();

        OrderResponses orderResponses = upbitSimulator.simulate(request);
        orderResponses.log();
        AccountResponses accountResponses = simulateUpbitTrader.getAccounts(UpbitAccountRequest.of(client));
        accountResponses.logKrw(upbitChart.recent(LocalDateTime.of(end, LocalTime.MAX)));
        accountResponses.logAll();
        Assertions.assertTrue(orderResponses.size() > 0);
    }
}
