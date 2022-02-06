package com.trading.chart.application.trade.request;

import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.response.AccountResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
public interface TradeRequest {
    List<MatchRequest> toMatchRequests();
    OrderRequest toOrderRequest(Double marketPrice);
    ChartRequest toOrderChartRequest();
    ChartRequest toOrderRecentChartRequest();
    AccountRequest toAccountRequest();
    String getMarket();
    String getClient();
    boolean isBuyOrder();
    boolean isSellOrder();
    boolean isLessPrice(AccountResponse account);
}
