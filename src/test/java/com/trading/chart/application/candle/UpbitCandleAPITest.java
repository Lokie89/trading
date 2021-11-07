package com.trading.chart.application.candle;

import com.trading.chart.application.candle.request.CandleRequest;
import com.trading.chart.application.candle.request.UpbitCandleRequest;
import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.candle.response.CandleResponse;
import com.trading.chart.application.common.CustomArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SeongRok.Oh
 * @since 2021/10/27
 */
@DisplayName("업비트 Candle 호출 테스트")
@SpringBootTest
public class UpbitCandleAPITest {

    @Autowired
    CandleAPI upbitCandleAPI;

    @DisplayName("개수로 캔들 호출하기")
    @Test
    void getCandlesWithCount() {
        final UpbitUnit unit = UpbitUnit.MINUTE_THREE;
        final String market = "KRW-BTC";
        final int count = 10;
        CandleRequest threeMinTenCountCandleRequest = UpbitCandleRequest.builder(unit, market).count(count).build();
        assertEquals(count, upbitCandleAPI.getCandles(threeMinTenCountCandleRequest).size());
    }

    @DisplayName("마지막 시간으로 캔들 호출하기")
    @Test
    void getCandlesWithTime() {
        final LocalDateTime time = LocalDateTime.now();
        final UpbitUnit unit = UpbitUnit.DAY;
        final String market = "KRW-BTC";
        CandleRequest threeMinLastTimeCandleRequest = UpbitCandleRequest.builder(unit, market).lastTime(time).build();
        assertTrue(time.isAfter(upbitCandleAPI.getCandles(threeMinLastTimeCandleRequest).getReverse(0).getCandleDateTimeKST()));
    }

    @DisplayName("마지막 시간, 개수로 캔들 호출하기")
    @Test
    void getCandlesWithTimeAndCount() {
        final LocalDateTime time = LocalDateTime.now();
        final UpbitUnit unit = UpbitUnit.WEEK;
        final String market = "KRW-BTC";
        final Integer count = 3;
        CandleRequest threeMinLastTimeCandleRequest = UpbitCandleRequest.builder(unit, market).lastTime(time).count(count).build();
        CustomArrayList<CandleResponse> candleList = upbitCandleAPI.getCandles(threeMinLastTimeCandleRequest);
        assertTrue(time.isAfter(candleList.getReverse(0).getCandleDateTimeKST()));
        assertEquals(count, candleList.size());
    }


}
