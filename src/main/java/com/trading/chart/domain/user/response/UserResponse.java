package com.trading.chart.domain.user.response;

import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.trade.request.TradeRequest;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2022/02/07
 */
public interface UserResponse {
    boolean isTradeStatus(TradeRequest tradeRequest);
    boolean isLimited();
    TradeRequest toTradeRequest(String market, LocalDateTime date, TradeType tradeType);
    boolean isAvailableTrade();
}
