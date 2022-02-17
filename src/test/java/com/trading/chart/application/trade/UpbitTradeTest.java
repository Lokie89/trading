package com.trading.chart.application.trade;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.DrawBollingerBandsUpbitChartRequest;
import com.trading.chart.application.chart.request.DrawLineUpbitChartRequest;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.order.Order;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trade.request.UpbitTradeRequest;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2022/01/08
 */
@DisplayName("업비트 트레이드 테스트")
@SpringBootTest
public class UpbitTradeTest {

    @Autowired
    Chart upbitChart;

    @Autowired
    Trade upbitTrade;

    @Autowired
    Order upbitOrder;

    @Autowired
    Trade simulateUpbitTrade;

    @Autowired
    Trader simulateUpbitTrader;

//    @DisplayName("자동 트레이드 테스트")
//    @Test
//    void tradeTest() {
//        final String market = "KRW-BTC";
//        final UpbitUnit unit = UpbitUnit.DAY;
//        final TradeStrategy strategy = TradeStrategy.LOWER_BOLLINGERBANDS;
//        final String client = "Traeuman";
//        final TradeType tradeType = TradeType.BUY;
//        final LocalDateTime date = LocalDateTime.of(2021, 12, 4, 9, 0, 1);
//        final int count = 1;
//
//        ChartRequest drawPriceLinesUpbitChartRequest = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).lastTime(date).count(count).build();
//
//        upbitChart.drawPriceLine(drawPriceLinesUpbitChartRequest);
//        ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
//                .lastTime(date)
//                .count(count)
//                .build();
//
//        upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
//        TradeRequest tradeRequest = UpbitTradeRequest.builder(client, tradeType, market, null)
//                .tradeResources(UpbitTradeResourceResponse.builder(tradeType, strategy, unit)
//                        .build())
//                .date(date)
//                .cash(5000)
//                .volume(1.0)
//                .build();
//
//        OrderResponse response = upbitTrade.trade(tradeRequest);
//        Assertions.assertEquals(market, response.getMarket());
//
//        String uuid = response.getUuid();
//        OrderCancelRequest cancelRequest = UpbitOrderCancelRequest.builder()
//                .client(client)
//                .uuid(uuid)
//                .build();
//
//        OrderResponse cancelResponse = upbitOrder.cancelOrder(cancelRequest);
//        assertEquals(uuid, cancelResponse.getUuid());
//    }

    @DisplayName("가상 트레이드 테스트")
    @Test
    void simulateTradeTest() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        final TradeStrategy strategy = TradeStrategy.LOWER_BOLLINGERBANDS;
        final String client = "million";
        final TradeType tradeType = TradeType.BUY;
        final LocalDateTime date = LocalDateTime.of(2021, 12, 4, 9, 0, 1);
        final int count = 1;

        ChartRequest drawPriceLinesUpbitChartRequest = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).lastTime(date).count(count).build();

        upbitChart.drawPriceLine(drawPriceLinesUpbitChartRequest);
        ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                .lastTime(date)
                .count(count)
                .build();

        upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
        TradeRequest tradeRequest = UpbitTradeRequest.builder(client, tradeType, market, null)
                .tradeResources(UpbitTradeResourceResponse.builder(tradeType, strategy, unit)
                        .build())
                .date(date)
                .cash(5000)
                .volume(1.0)
                .build();

        OrderResponse orderResponse = simulateUpbitTrade.trade(tradeRequest);

        Assertions.assertEquals(market, orderResponse.getMarket());

    }
}
