package com.trading.chart.application.exchange;

import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;

/**
 * @author SeongRok.Oh
 * @since 2022/02/05
 */
@RequiredArgsConstructor
@Component
public class UpbitExchange implements Exchange {

    private final Trader upbitTrader;
    private final TradeItem upbitTradeItem;

    @Async
    @Override
    public OrderResponses exchange(UserResponse user, LocalDateTime date) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        AccountResponses accounts = upbitTrader.getAccounts(UpbitAccountRequest.of(user.getUpbitClient()));
        SortedSet<ItemResponse> items = upbitTradeItem.getKrwItems();
        for (ItemResponse item : items) {
            final String market = item.getName();
            orderResponseList.addAll(exchangeMarket(user, market, date, accounts));
        }
        return OrderResponses.of(orderResponseList);
    }

    // TODO : abstract 메서드로 나눠보기
    private List<OrderResponse> exchangeMarket(UserResponse user, String market, LocalDateTime date, AccountResponses accounts) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        TradeRequest buyTradeRequest = user.toTradeRequest(market, date, TradeType.BUY, accounts);
        if (user.isTradeStatus(buyTradeRequest) && user.isAvailableTrade() && user.isLimited()) {
            OrderResponse orderResponse = upbitTrader.trade(buyTradeRequest);
            if (Objects.nonNull(orderResponse)) {
                user.apply(orderResponse);
                orderResponses.add(orderResponse);
            }
        }

        TradeRequest sellTradeRequest = user.toTradeRequest(market, date, TradeType.SELL, accounts);
        if (user.isTradeStatus(sellTradeRequest) && accounts.stream()
                .filter(account -> market.replace("KRW-", "").equals(account.getCurrency()))
                .anyMatch(AccountResponse::isOwn)) {
            OrderResponse orderResponse = upbitTrader.trade(sellTradeRequest);
            if (Objects.nonNull(orderResponse)) {
                user.apply(orderResponse);
                orderResponses.add(orderResponse);
            }
        }
        return orderResponses;
    }

}
