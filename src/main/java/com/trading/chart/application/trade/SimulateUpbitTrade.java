package com.trading.chart.application.trade;

import com.trading.chart.application.chart.Chart;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.match.Match;
import com.trading.chart.application.order.Order;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
@RequiredArgsConstructor
@Component
public class SimulateUpbitTrade implements Trade {

    private final Match upbitMatch;
    private final Order simulateUpbitOrder;
    private final Chart cacheUpbitChart;


    @Override
    public OrderResponse trade(TradeRequest request) {
        boolean isMatched = upbitMatch.match(request.toMatchRequests());
        if (isMatched) {
            ChartRequest orderChartRequest = request.toOrderChartRequest();
            cacheUpbitChart.caching(orderChartRequest);
            Double marketPrice = cacheUpbitChart.getChart(orderChartRequest).getLast().getTradePrice();
            return simulateUpbitOrder.order(request.toOrderRequest(marketPrice));
        }
        return null;
    }
}
