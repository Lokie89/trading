package com.trading.chart.application.order;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.trader.response.OrderResponse;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@DisplayName("업비트 주문 테스트")
@SpringBootTest
public class UpbitOrderTest {

    @Autowired
    Order upbitOrder;

    @DisplayName("업비트 주문하기")
    @Test
    void upbitOrderTest() {
        final String market = "KRW-BTC";
        final TradeType tradeType = TradeType.BUY;
        final Integer cash = 5000;
        final Double price = 321.35;
        OrderRequest request = UpbitOrderRequest.builder().item(market).tradeType(tradeType).cash(cash).price(price).build();
        OrderResponse response = upbitOrder.order(request);
        assertEquals(market, response.getMarket());
    }
}
