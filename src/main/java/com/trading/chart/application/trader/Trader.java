package com.trading.chart.application.trader;

import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.UpbitUserResponse;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface Trader {
    AccountResponses getAccounts(AccountRequest request);
    OrderResponse trade(TradeRequest request);
}
