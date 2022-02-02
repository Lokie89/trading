package com.trading.chart.application.trade.request;

import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.order.request.OrderRequest;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
public interface TradeRequest {
    List<MatchRequest> toMatchRequests();
    OrderRequest toOrderRequest();
}
