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
import com.trading.chart.domain.user.response.UpbitUserResponse;
import com.trading.chart.repository.user.UpbitUserRepository;
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
    private final UpbitUserRepository userRepository;

    @Override
    public AccountResponses getAccounts(AccountRequest request) {
        return getAccounts(request.getClient());
    }

    @Override
    public OrderResponse order(OrderRequest orderRequest) {
        validateAvailableTrade(orderRequest);
        return upbitOrder.order(orderRequest);
    }

    private AccountResponses getAccounts(String client) {
        String response = callAPI.get(accountUrl, upbitTradeAPIHeader.getHeaders(client));
        UpbitAccount[] accounts = ConvertType.stringToType(response, UpbitAccount[].class);
        return AccountResponses.of(Arrays.asList(accounts));
    }

    private void validateAvailableTrade(OrderRequest orderRequest) {
        final String client = orderRequest.getClient();
        AccountResponses accounts = getAccounts(client);
        UpbitUserResponse user = userRepository.findById(client).orElseThrow(RuntimeException::new).toDto(accounts);
        if (user.isTradeStatus(orderRequest)) {
            throw new RuntimeException(); // TODO : Custom Exception;
        }
        if (user.isAvailableTrade(orderRequest)) {
            throw new RuntimeException(); // TODO : Custom Exception;
        }
    }


}
