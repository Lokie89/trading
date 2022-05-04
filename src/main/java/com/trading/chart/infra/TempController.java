package com.trading.chart.infra;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.SimpleUpbitChartRequest;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.message.Messenger;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.simulator.request.SimulatorRequest;
import com.trading.chart.application.simulator.request.UpbitSimulatorRequest;
import com.trading.chart.domain.user.ExchangePlatform;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.response.TradeResourceResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2022/03/17
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class TempController {
    private final Chart cacheUpbitChart;
    private final Messenger upbitSimulatorMessenger;

    private final UpbitUserRepository upbitUserRepository;

    @GetMapping("/test")
    public ChartResponses get(@RequestParam String market, @RequestParam UpbitUnit unit, @RequestParam Integer count) {
        ChartResponses chartResponses = cacheUpbitChart.getChart(SimpleUpbitChartRequest.builder(market, unit).count(count).build());
        log.info("######### {}", chartResponses.size());
        return chartResponses;
    }

    @GetMapping("/simulate")
    public void simulate() {
        SimulatorRequest request = UpbitSimulatorRequest.builder(
                        LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 2)
                )
                .tradeResources(
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.BUY, TradeStrategy.LOWER_BOLLINGERBANDS, UpbitUnit.MINUTE_FIVE).build(),
                        TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.SELL, TradeStrategy.HIGHER_BOLLINGERBANDS, UpbitUnit.MINUTE_FIVE).build()
                ).client(upbitUserRepository.findByUpbitClient("tjdfhrdk10@naver.com").orElse(null))
                .cashAtOnce(50000)
                .seed(10000000)
                .build();
        upbitSimulatorMessenger.send(request);
    }

}
