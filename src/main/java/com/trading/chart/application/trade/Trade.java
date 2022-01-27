package com.trading.chart.application.trade;

import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
public interface Trade {
    OrderResponse trade(TradeRequest request);
}
