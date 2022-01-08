package com.trading.chart.application.trader;

import com.trading.chart.application.order.Order;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.application.tunnel.TradeAPIHeader;
import com.trading.chart.common.ConvertType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */

@RequiredArgsConstructor
@Component
public class UpbitTrader implements Trader {

    private final String accountUrl = "https://api.upbit.com/v1/accounts";
    private final CallAPI callAPI;
    private final TradeAPIHeader upbitTradeAPIHeader;
    private final Order upbitOrder;

    @Override
    public AccountResponses getAccounts(AccountRequest request) {
        String response = callAPI.get(accountUrl, upbitTradeAPIHeader.getHeaders(request.getClient()));
        UpbitAccount[] accounts = ConvertType.stringToType(response, UpbitAccount[].class);
        return AccountResponses.of(Arrays.asList(accounts));
    }

    @Override
    public OrderResponse order(OrderRequest orderRequest) {
        return upbitOrder.order(orderRequest);
    }


}
