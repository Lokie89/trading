package com.trading.chart.application.match;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.request.DrawBollingerBandsUpbitChartRequest;
import com.trading.chart.application.chart.request.DrawLineUpbitChartRequest;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.match.request.UpbitMatchRequest;
import com.trading.chart.application.match.response.MatchResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/12/03
 */
@DisplayName("전략 확인 테스트")
@SpringBootTest
public class UpbitMatchTest {

    @Autowired
    Match upbitMatch;

    @Autowired
    Chart upbitChart;

    @DisplayName("전략 확인 테스트")
    @Test
    void matchTest() {
        final String market = "KRW-SSX";
        final LinePeriod period = LinePeriod.get(20).orElseThrow(RuntimeException::new);
        final UpbitUnit unit = UpbitUnit.DAY;
        final int count = 10;
        final LocalDateTime lastTime = LocalDateTime.of(2021, 9, 21, 22, 5);
        ChartRequest drawLineRequest = DrawLineUpbitChartRequest.builder(market, period, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawPriceLine(drawLineRequest);
        ChartRequest drawBollingerBandsUpbitChartRequest = DrawBollingerBandsUpbitChartRequest.builder(market, unit).count(count).lastTime(lastTime).build();
        upbitChart.drawBollingerBands(drawBollingerBandsUpbitChartRequest);
        MatchRequest bollingerBandsMatchRequest
                = UpbitMatchRequest.builder(market, UpbitUnit.DAY, TradeStrategy.LOWER_BOLLINGERBANDS)
                .standard(0)
                .range(2)
                .matchMin(1)
                .matchMax(30)
                .date(lastTime)
                .build();
        boolean matchTest = upbitMatch.match(bollingerBandsMatchRequest);
        Assertions.assertTrue(matchTest);

        MatchRequest bollingerBandsMatchRequest2
                = UpbitMatchRequest.builder(market, UpbitUnit.DAY, TradeStrategy.LOWER_BOLLINGERBANDS)
                .standard(0)
                .range(3)
                .matchMin(3)
                .matchMax(30)
                .date(lastTime)
                .build();
        boolean matchTest2 = upbitMatch.match(bollingerBandsMatchRequest2);
        Assertions.assertFalse(matchTest2);
    }

}
