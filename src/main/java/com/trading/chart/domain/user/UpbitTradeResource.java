package com.trading.chart.domain.user;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.application.match.request.TradeStrategy;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;

import javax.persistence.*;

/**
 * @author SeongRok.Oh
 * @since 2022/02/02
 */
@Entity
public class UpbitTradeResource {

    @GeneratedValue
    @Id
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UpbitUser user;
    private TradeStrategy strategy;
    private TradeType tradeType;
    private UpbitUnit unit;
    private Integer matchStandard;
    private Integer matchRange;
    private Integer matchMin;
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
