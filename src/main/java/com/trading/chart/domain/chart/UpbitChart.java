package com.trading.chart.domain.chart;


import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        indexes = @Index(name = "index_chart", columnList = "time, market, unit"),
        uniqueConstraints = {
                @UniqueConstraint(name = "chartKey", columnNames = {"market", "unit", "time"})
        }
)
@Entity
public class UpbitChart implements ChartEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String market;
    private LocalDateTime time;
    private Double lowPrice;
    private Double openingPrice;
    private Double tradePrice;
    private Double highPrice;
    private Double volume;
    @Enumerated(EnumType.STRING)
    private UpbitUnit unit;
    private Double changePrice;
    private Double changeRate;

    @OneToMany(mappedBy = "upbitChart", fetch = FetchType.EAGER)
    private Set<UpbitChartPriceLine> priceLines;

    private Double upperBollingerBand;
    private Double downBollingerBand;
    private Double rsi;
    private Double rsiSignal;


    public UpbitChartResponse toDto() {
        return UpbitChartResponse.builder()
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
                .priceLines(priceLines.stream().map(UpbitChartPriceLine::toDto).collect(Collectors.toSet()))
                .upperBollingerBand(upperBollingerBand)
                .downBollingerBand(downBollingerBand)
                .rsi(rsi)
                .rsiSignal(rsiSignal)
                .build();
    }
}
