package com.trading.chart.application.trade;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.*;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trade.request.UpbitTradeRequest;
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

    @DisplayName("자동 트레이드 하기 테스트")
    @Test
    void tradeTest() {
        final String market = "KRW-BTC";
        final UpbitUnit unit = UpbitUnit.DAY;
        final TradeStrategy strategy = TradeStrategy.LOWER_BOLLINGERBANDS;
        final String client = "tjdfhrdk10@naver.com";
        final TradeType side = TradeType.BUY;
        final LocalDateTime date = LocalDateTime.of(2021, 12, 4, 9, 0, 1);
        final int count = 1;

        ChartRequest drawPriceLinesUpbitChartRequest = DrawLineUpbitChartRequest.builder(market, LinePeriod.TWENTY, unit).lastTime(date).count(count).build();

        upbitChart.drawPriceLine(drawPriceLinesUpbitChartRequest);
        ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit)
                .lastTime(date)
                .count(count)
                .build();

        upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
        TradeRequest tradeRequest = UpbitTradeRequest.builder(market, unit, strategy, client, side)
                .date(date)
                .build();

        OrderResponse orderResponse = upbitTrade.trade(tradeRequest);
        Assertions.assertEquals(market, orderResponse.getMarket());
    }
}
