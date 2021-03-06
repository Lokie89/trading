package com.trading.chart.application.message;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.scheduler.UpbitChartPublishScheduler;
import com.trading.chart.application.scheduler.UpbitChartSubscribeScheduler;
import com.trading.chart.application.scheduler.UpbitSimulateScheduler;
import com.trading.chart.application.simulator.Simulator;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.domain.user.ExchangePlatform;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2022/04/07
 */

@Disabled
@ActiveProfiles("test")
@DisplayName("메신저 테스트")
@SpringBootTest
public class MessengerTest {
    @Autowired
    Messenger upbitSimulatorMessenger;

    @Autowired
    UpbitChartPublishScheduler publishScheduler;

    @Autowired
    UpbitChartSubscribeScheduler subscribeScheduler;

    @Autowired
    UpbitSimulateScheduler simulateScheduler;

    @Autowired
    Simulator upbitSimulator;

    @Autowired
    UpbitUserRepository upbitUserRepository;

    @Autowired
    Chart cacheUpbitChart;

    @DisplayName("시뮬레이터 메신저 테스트")
    @Test
    void simulateMessengerTest() throws InterruptedException {
        UpbitUser upbitUser = upbitUserRepository.findByUpbitClient("tjdfhrdk10@naver.com").orElse(null);
        SimulatorRequest request = UpbitSimulatorRequest.builder(
                        LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1)
                )
                .tradeResources(
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.BUY, TradeStrategy.LOWER_BOLLINGERBANDS, UpbitUnit.MINUTE_FIVE).build(),
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.SELL, TradeStrategy.HIGHER_BOLLINGERBANDS, UpbitUnit.MINUTE_FIVE).build()
                ).client(upbitUser)
                .cashAtOnce(50000)
                .seed(10000000)
                .build();


        // Simulate Request
        upbitSimulatorMessenger.send(request);
        // Chart List
        publishScheduler.updateMarket();
        subscribeScheduler.callUpbitApiRequest();
        Thread.sleep(1000);
        // Call API Chart
        subscribeScheduler.runWithThread();
        subscribeScheduler.callUpbitApiRequest();
        Thread.sleep(1000);
        subscribeScheduler.callUpbitApiRequest();
        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
//        subscribeScheduler.callUpbitApiRequest();
//        Thread.sleep(1000);
        // Draw Chart
        subscribeScheduler.runWithThread();
        // Check Chart, Simulating, Save Result, Alarm
        simulateScheduler.carry();

        cacheUpbitChart.archive();

        Assertions.assertNotNull(upbitSimulator.getReport(upbitUser));
    }


}
