package com.trading.chart.domain.chart;


import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.chart.response.UpbitChartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "chartKey", columnNames = {"time", "market", "unit"})
        }
)
@Entity
public class UpbitChart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String market;
    @Column(nullable = false)
    private LocalDateTime time;
    private Double lowPrice;
    private Double openingPrice;
    private Double tradePrice;
    private Double highPrice;
    private Double volume;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UpbitUnit unit;
    private Double changePrice;
    private Double changeRate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "upbit_chart_price_line",
            joinColumns = @JoinColumn(name = "upbit_chart_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"upbit_chart_id", "period"}))
    private Set<ChartPriceLine> priceLines;

    @Column(nullable = false)
    private Double upperBollingerBand;
    @Column(nullable = false)
    private Double downBollingerBand;
    @Column(nullable = false)
    private Double rsi;
    private Double rsiSignal;


    public UpbitChartResponse toDto() {
        return UpbitChartResponse.builder()
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
}
