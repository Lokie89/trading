package com.trading.chart.domain.chart;


import com.trading.chart.application.candle.request.UpbitUnit;
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
@Table(indexes = @Index(name = "index_chart", columnList = "time, market, unit"))
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
}
