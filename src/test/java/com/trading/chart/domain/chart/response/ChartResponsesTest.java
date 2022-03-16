package com.trading.chart.domain.chart.response;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/04/04
 */
@ActiveProfiles("test")
@DisplayName("차트 테스트")
@SpringBootTest
public class ChartResponsesTest {

    @DisplayName("더하기 테스트")
    @Test
    void addAllTest() {
        List<ChartResponse> chartList1 = new ArrayList<>();
        chartList1.add(UpbitChartResponse.builder().market("KRW-BTC5").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 4, 4, 5, 30)).build());
        chartList1.add(UpbitChartResponse.builder().market("KRW-BTC6").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 4, 4, 6, 30)).build());
        chartList1.add(UpbitChartResponse.builder().market("KRW-BTC7").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 4, 4, 7, 30)).build());

        ChartResponses chart = ChartResponses.of(chartList1);


        List<ChartResponse> chartList2 = new ArrayList<>();
        chartList2.add(UpbitChartResponse.builder().market("KRW-BTC5-1").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 4, 4, 5, 30)).build());
        chartList2.add(UpbitChartResponse.builder().market("KRW-BTC6-1").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 4, 4, 6, 30)).build());
        chartList2.add(UpbitChartResponse.builder().market("KRW-BTC7-1").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 4, 4, 7, 30)).build());

        chart.addAll(ChartResponses.of(chartList2));

        Assertions.assertEquals("KRW-BTC7-1", chart.getLast().getMarket());

    }

    @DisplayName("가져오기 테스트")
    @Test
    void subSetTest() {
        List<ChartResponse> chartList1 = new ArrayList<>();
        chartList1.add(UpbitChartResponse.builder().market("KRW-BTC5").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 1, 1, 9, 0)).build());
        chartList1.add(UpbitChartResponse.builder().market("KRW-BTC5").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 1, 2, 9, 0)).build());
        chartList1.add(UpbitChartResponse.builder().market("KRW-BTC5").unit(UpbitUnit.DAY).time(LocalDateTime.of(2022, 1, 3, 9, 0)).build());

        ChartResponses chart = ChartResponses.of(chartList1);

        ChartResponses subSetCharts = chart.substitute(
                UpbitChartResponse.builder().time(LocalDateTime.of(2021, 12, 31, 0, 0)).build(),
                UpbitChartResponse.builder().time(LocalDateTime.of(2022, 1, 1, 0, 1)).build()
        );
        Assertions.assertEquals(1, subSetCharts.size());
    }

}
