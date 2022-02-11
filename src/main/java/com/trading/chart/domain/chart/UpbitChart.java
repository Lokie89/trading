package com.trading.chart.domain.chart;


import com.trading.chart.application.candle.request.UpbitUnit;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */

@Table(indexes = @Index(name = "index_chart", columnList = "time,market,unit"))
@Entity
public class UpbitChart {
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
    private UpbitUnit unit;
    private Double changePrice;
    private Double changeRate;
}
