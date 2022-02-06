package com.trading.chart.application.exchange;

import com.trading.chart.application.item.TradeItem;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.domain.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        List<ItemResponse> items = upbitTradeItem.getItems().stream()
                .filter(ItemResponse::isKrwMarket)
                .collect(Collectors.toList());
        for (ItemResponse item : items) {
            final String market = item.getName();
            orderResponseList.addAll(exchangeMarket(user, market, date));
        }
        return OrderResponses.of(orderResponseList);
    }

    private List<OrderResponse> exchangeMarket(UserResponse user, String market, LocalDateTime date) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        TradeRequest buyTradeRequest = user.toTradeRequest(market, date, TradeType.BUY);
        if (user.isTradeStatus(buyTradeRequest) && user.isAvailableTrade() && !user.isLimited()) {
            orderResponseList.add(simulateUpbitTrader.trade(buyTradeRequest));
        }

        TradeRequest sellTradeRequest = user.toTradeRequest(market, date, TradeType.SELL);
        if (user.isTradeStatus(sellTradeRequest)) {
            orderResponseList.add(simulateUpbitTrader.trade(sellTradeRequest));
        }
        return orderResponseList;
    }
}
