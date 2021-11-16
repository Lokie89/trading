package com.trading.chart.application.trader;

import com.trading.chart.application.trader.request.DealtRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.DealtResponse;
import com.trading.chart.application.trader.response.UpbitAccount;
import com.trading.chart.application.trader.response.UpbitDealtResponse;
import com.trading.chart.application.tunnel.CallAPI;
import com.trading.chart.application.tunnel.TradeAPIHeader;
import com.trading.chart.common.ConvertType;
import com.trading.chart.common.CustomArrayList;
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

    private final String accountUrl = "https://api.upbit.com/v1/accounts";
    private final String dealtUrl = "https://api.upbit.com/v1/trades/ticks";
    private final CallAPI callAPI;
    private final TradeAPIHeader upbitTradeAPIHeader;

    @Override
    public List<AccountResponse> getAccounts(String id) {
        String response = callAPI.get(accountUrl, upbitTradeAPIHeader.getHeaders(id));
        UpbitAccount[] accounts = ConvertType.stringToType(response, UpbitAccount[].class);
        return new CustomArrayList<>(Arrays.asList(accounts));
    }

    @Override
    public List<DealtResponse> getRecentlyDealt(DealtRequest dealtRequest) {
        String response = callAPI.get(dealtUrl + "?" + ConvertType.ObjectToQueryString(dealtRequest, "account"),
                upbitTradeAPIHeader.getHeaders(dealtRequest.getAccount()));
        UpbitDealtResponse[] dealt = ConvertType.stringToType(response, UpbitDealtResponse[].class);
        return new CustomArrayList<>(Arrays.asList(dealt));
    }
}
