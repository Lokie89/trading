package com.trading.chart.application.exchange;

import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/02/07
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitExchange implements Exchange {

    private final Trader simulateUpbitTrader;
    private final TradeItem upbitTradeItem;

    @Override
    public OrderResponses exchange(UserResponse user, LocalDateTime date) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        AccountResponses accounts = simulateUpbitTrader.getAccounts(UpbitAccountRequest.of(user.getId()));
        List<ItemResponse> items = upbitTradeItem.getItems().stream()
                .filter(ItemResponse::isKrwMarket)
                .collect(Collectors.toList());
        int i = 0;
        for (ItemResponse item : items) {
            if (i > 0) {
                break;
            }
            final String market = item.getName();
            orderResponseList.addAll(exchangeMarket(user, market, date, accounts));
            i++;
        }
        return OrderResponses.of(orderResponseList);
    }

    private List<OrderResponse> exchangeMarket(UserResponse user, String market, LocalDateTime date, AccountResponses accounts) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        TradeRequest buyTradeRequest = user.toTradeRequest(market, date, TradeType.BUY, accounts);
        if (user.isTradeStatus(buyTradeRequest) && user.isAvailableTrade() && user.isLimited()) {
            OrderResponse orderResponse = simulateUpbitTrader.trade(buyTradeRequest);
            if (Objects.nonNull(orderResponse)) {
                orderResponseList.add(orderResponse);
            }
        }

        TradeRequest sellTradeRequest = user.toTradeRequest(market, date, TradeType.SELL, accounts);
        if (user.isTradeStatus(sellTradeRequest)
                && accounts.stream()
                .filter(account -> market.replace("KRW-", "").equals(account.getCurrency()))
                .anyMatch(AccountResponse::isOwn)) {
            OrderResponse orderResponse = simulateUpbitTrader.trade(sellTradeRequest);
            if (Objects.nonNull(orderResponse)) {
                orderResponseList.add(orderResponse);
            }
        }
        return orderResponseList;
    }
}
