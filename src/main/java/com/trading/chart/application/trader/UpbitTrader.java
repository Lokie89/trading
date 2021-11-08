package com.trading.chart.application.trader;

import com.trading.chart.application.trader.response.OrderResponse;
import com.trading.chart.common.ConvertType;
import com.trading.chart.common.CustomArrayList;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.application.tunnel.TradeAPIHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */

@RequiredArgsConstructor
@Component
public class UpbitTrader implements Trader {

    private final String url = "https://api.upbit.com/v1/accounts";
    private final CallAPI callAPI;
    private final TradeAPIHeader upbitTradeAPIHeader;

    @Override
    public List<AccountResponse> getAccounts(String id) {
        String response = callAPI.get(url, upbitTradeAPIHeader.getHeaders(id));
        UpbitAccount[] accounts = ConvertType.stringToType(response, UpbitAccount[].class);
        return new CustomArrayList<>(Arrays.asList(accounts));
    }

    @Override
    public List<OrderResponse> getOrders(String id) {
        return null;
    }
}
