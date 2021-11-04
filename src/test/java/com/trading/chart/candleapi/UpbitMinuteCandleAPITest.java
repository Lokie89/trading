package com.trading.chart.candleapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SeongRok.Oh
 * @since 2021/10/27
 */
@DisplayName("업비트 API 호출 테스트")
@SpringBootTest
public class UpbitMinuteCandleAPITest {

    @Autowired
    CandleAPI upbitCandleAPI;

    @DisplayName("분 단위로 캔들 호출하기")
    @Test
    void getCandlesWithUnit() {
        final int unit = 3;
        assertFalse(
                upbitCandleAPI.getCandles(unit).stream()
                        .anyMatch((callApiResponse) -> callApiResponse.getUnit() != unit)
        );
    }

    @DisplayName("개수로 캔들 호출하기")
    @Test
    void getCandlesWithCount() {
        final int unit = 3;
        final int count = 10;
        assertEquals(count, upbitCandleAPI.getCandles(unit, count).size());
    }

    @DisplayName("마켓으로 캔들 호출하기")
    @Test
    void getCandlesWithMarket() {
        final int unit = 3;
        final String market = "KRW-BTC";
        assertFalse(
                upbitCandleAPI.getCandles(unit, market).stream()
                        .anyMatch((callApiResponse) -> !callApiResponse.getMarket().equals(market))
        );
    }


    @DisplayName("마지막 시간으로 캔들 호출하기")
    @Test
    void getCandlesWithTime() {
        final int unit = 3;
        final String timeStr = "2021-10-27T22:28:25";
        final LocalDateTime time = LocalDateTime.parse(timeStr, DateTimeFormatter.ISO_INSTANT);
        assertTrue(time.isAfter(upbitCandleAPI.getCandles(unit, time).getReverse(0).getCandleDateTimeKST()));
    }


}
