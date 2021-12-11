package com.trading.chart.application.trader;

import com.trading.chart.application.trader.request.DealtRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.DealtResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public interface Trader {
    AccountResponses getAccounts(String client);
    List<DealtResponse> getRecentlyDealt(DealtRequest dealtRequest);
}
