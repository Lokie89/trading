package com.trading.chart.application.chart.response;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.candle.response.UpbitCandleResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@EqualsAndHashCode(of = {"market", "unit", "time"})
@Getter
public class UpbitChartResponse implements ChartResponse {
    private String market;
    private LocalDateTime time;
    private Double lowPrice;
    private Double openingPrice;
    private Double tradePrice;
    private Double highPrice;
    private Double volume;
    private UpbitUnit unit;
    private Double changeRate;

    private Set<ChartPriceLine> priceLines;

    public UpbitChartResponse(String market, LocalDateTime time, UpbitUnit unit) {
        this.market = market;
        this.time = time;
        this.unit = unit;
    }

    public UpbitChartResponse(UpbitCandleResponse upbitCandleResponse) {
        this.market = upbitCandleResponse.getMarket();
        this.time = upbitCandleResponse.getCandleDateTimeKST();
        this.lowPrice = upbitCandleResponse.getLowPrice();
        this.openingPrice = upbitCandleResponse.getOpeningPrice();
        this.tradePrice = upbitCandleResponse.getTradePrice();
        this.highPrice = upbitCandleResponse.getHighPrice();
        this.volume = upbitCandleResponse.getAccTradeVolume();
        this.unit = UpbitUnit.get(upbitCandleResponse.getUnit());
        this.changeRate = upbitCandleResponse.getChangeRate();
    }

    @Override
    public void drawPriceLine(ChartPriceLine line) {
        if (Objects.isNull(priceLines)) {
            priceLines = new HashSet<>();
        }
        this.priceLines.add(line);
    }
}
