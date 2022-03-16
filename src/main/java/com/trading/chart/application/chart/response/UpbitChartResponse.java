package com.trading.chart.application.chart.response;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.request.LinePeriod;
import com.trading.chart.domain.chart.ChartPriceLine;
import com.trading.chart.domain.chart.UpbitChart;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = {"market", "unit", "time"})
@Getter
public class UpbitChartResponse implements ChartResponse {
    private Long id;
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

    @Builder.Default
    private Set<ChartPriceLine> priceLines = new HashSet<>();

    private Double upperBollingerBand;
    private Double downBollingerBand;

    private Double rsi;
    private Double rsiSignal;


    public UpbitChartResponse(String market, LocalDateTime time, UpbitUnit unit) {
        this.market = market;
        this.time = time;
        this.unit = unit;
    }

    @Override
    public UpbitChart toEntity() {
        return UpbitChart.builder()
                .id(id)
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
                .priceLines(priceLines)
                .upperBollingerBand(upperBollingerBand)
                .downBollingerBand(downBollingerBand)
                .rsi(rsi)
                .rsiSignal(rsiSignal)
                .build();
    }

    @Override
    public boolean isCreated() {
        return Objects.nonNull(id);
    }

    @Override
    public void updateExcludeId(ChartResponse chartResponse) {
        UpbitChartResponse toUpdate = (UpbitChartResponse) chartResponse;
        this.market = toUpdate.market;
        this.time = toUpdate.time;
        this.lowPrice = toUpdate.lowPrice;
        this.openingPrice = toUpdate.openingPrice;
        this.tradePrice = toUpdate.tradePrice;
        this.highPrice = toUpdate.highPrice;
        this.volume = toUpdate.volume;
        this.unit = toUpdate.unit;
        this.changePrice = toUpdate.changePrice;
        this.changeRate = toUpdate.changeRate;
    }

    @Override
    public boolean isSavable() {
        return Objects.nonNull(upperBollingerBand)
                && Objects.nonNull(downBollingerBand)
                && Objects.nonNull(rsi);
    }

    @Override
    public void drawPriceLine(ChartPriceLine line) {
        this.priceLines.remove(line);
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
