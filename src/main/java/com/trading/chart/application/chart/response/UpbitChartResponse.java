package com.trading.chart.application.chart.response;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.candle.response.UpbitCandleResponse;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.domain.chart.ChartEntity;
import com.trading.chart.domain.chart.UpbitChart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@Builder
@AllArgsConstructor
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
    private Double changePrice;
    private Double changeRate;

    private Set<ChartPriceLine> priceLines;

    private double upperBollingerBand;
    private double downBollingerBand;

    private double rsi;
    private double rsiSignal;


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
        this.changePrice = upbitCandleResponse.getChangePrice();
        this.changeRate = upbitCandleResponse.getChangeRate();
    }

    @Override
    public ChartEntity toEntity() {
        return UpbitChart.builder()
                .market(market)
                .time(time)
                .lowPrice(lowPrice)
                .openingPrice(openingPrice)
                .tradePrice(tradePrice)
                .highPrice(highPrice)
                .volume(volume)
                .unit(unit)
                .changePrice(changePrice)
                .changeRate(changeRate)
                .priceLines(priceLines.stream()
                        .map(ChartPriceLine::toUpbitChartPriceLine)
                        .collect(Collectors.toSet()))
                .upperBollingerBand(upperBollingerBand)
                .downBollingerBand(downBollingerBand)
                .rsi(rsi)
                .rsiSignal(rsiSignal)
                .build();
    }

    @Override
    public void drawPriceLine(ChartPriceLine line) {
        if (Objects.isNull(priceLines)) {
            priceLines = new HashSet<>();
        }
        this.priceLines.add(line);
    }

    @Override
    public void drawBollingerBands(Double standardDeviation) {
        Optional<ChartPriceLine> twentyPriceLine
                = priceLines.stream()
                .filter(priceLine -> LinePeriod.TWENTY.equals(priceLine.getPeriod()))
                .findAny();
        if (twentyPriceLine.isPresent()) {
            this.upperBollingerBand = twentyPriceLine.get().getValue() + standardDeviation * 2;
            this.downBollingerBand = twentyPriceLine.get().getValue() - standardDeviation * 2;
        }
    }

    @Override
    public void drawRsi(Double rsi) {
        this.rsi = rsi;
    }

}
