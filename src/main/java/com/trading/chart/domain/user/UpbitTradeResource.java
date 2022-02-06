package com.trading.chart.domain.user;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @author SeongRok.Oh
 * @since 2022/02/02
 */
@Entity
public class UpbitTradeResource {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UpbitUser user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeStrategy strategy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UpbitUnit unit;

    @ColumnDefault(value = "0")
    @Column(nullable = false)
    private Integer matchStandard;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Integer matchRange;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Integer matchMin;

    @ColumnDefault(value = "0x7fffffff")
    @Column(nullable = false)
    private Integer matchMax;

    public UpbitTradeResourceResponse toDto() {
        return UpbitTradeResourceResponse.builder(tradeType, strategy, unit)
                .matchStandard(matchStandard)
                .matchRange(matchRange)
                .matchMin(matchMin)
                .matchMax(matchMax)
                .build();
    }

}
