package com.trading.chart.application.exchange;

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
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;
import com.trading.chart.domain.user.response.UpbitUserResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/02/05
 */
@DisplayName("거래소 테스트")
@SpringBootTest
public class ExchangeTest {

    @Autowired
    Chart upbitChart;

    @Autowired
    TradeItem upbitTradeItem;

    @Autowired
    Exchange upbitExchange;

    @Autowired
    Exchange simulateUpbitExchange;

    @Autowired
    UpbitUserRepository userRepository;

    @Autowired
    Trader upbitTrader;

    @Autowired
    Trader simulateUpbitTrader;

    final UpbitUnit unit = UpbitUnit.MINUTE_ONE;
    final LocalDateTime date = LocalDateTime.of(2022, 2, 11, 18, 2, 1);

    @BeforeEach
    void setUp() {
        final int count = 5;

        List<ItemResponse> items = upbitTradeItem.getItems().stream()
                .filter(ItemResponse::isKrwMarket)
                .collect(Collectors.toList());
        for (ItemResponse item : items) {
            final String market = item.getName();
            ChartRequest drawPriceLinesUpbitChartRequest = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).lastTime(date).count(count).build();
            upbitChart.drawPriceLine(drawPriceLinesUpbitChartRequest);

            ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                    .lastTime(date)
                    .count(count)
                    .build();
            upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
        }
    }

//    @DisplayName("거래소 계정으로 거래 테스트")
//    @Test
//    void exchangeTest() {
//        final String client = "tjdfhrdk10@naver.com";
//        final LocalDateTime date = LocalDateTime.of(2021, 12, 4, 9, 0, 1);
//
//        UpbitUserResponse user = userRepository
//                .findById(client)
//                .orElseThrow(RuntimeException::new)
//                .toDto(upbitTrader.getAccounts(UpbitAccountRequest.of(client)));
//
//        OrderResponses orderResponses = upbitExchange.exchange(user, date);
//        Assertions.assertTrue(orderResponses.size() > 0);
//    }

    @DisplayName("가상 거래소 계정으로 거래 테스트")
    @Test
    void simulateExchangeTest() {
        final String client = "million";
        UpbitUserResponse user = UpbitUserResponse.builder()
                .id(client)
                .accounts(AccountResponses.of(UpbitAccount.of("KRW",1000000.0,0.0)))
                .cashAtOnce(50000)
                .tradeResources(
                        UpbitTradeResourceResponse.builder(TradeType.BUY, TradeStrategy.LOWER_BOLLINGERBANDS, unit)
                                .matchRange(3).matchMin(3).matchMax(3).build()
                )
                .build();

        OrderResponses orderResponses = simulateUpbitExchange.exchange(user, date);
        AccountResponses accounts = simulateUpbitTrader.getAccounts(UpbitAccountRequest.of(client));
        Assertions.assertTrue(orderResponses.size() > 0);
    }


}
