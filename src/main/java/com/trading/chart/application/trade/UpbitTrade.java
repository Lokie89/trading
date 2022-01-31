package com.trading.chart.application.trade;

import com.trading.chart.application.match.Match;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.Trader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
@RequiredArgsConstructor
@Component
public class UpbitTrade implements Trade {

    private final Match upbitMatch;
    private final Trader upbitTrader;

    @Override
    public OrderResponse trade(TradeRequest request) {
        boolean isMatched = upbitMatch.match(request.toMatchRequest());
        if (isMatched) {
            return upbitTrader.order(request.toOrderRequest());
        }
        return null;
    }
}
