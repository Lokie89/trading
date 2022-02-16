package com.trading.chart.application.order;

import com.trading.chart.application.order.request.*;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.OrderResponses;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2021-11-10
 */
@DisplayName("업비트 주문 테스트")
@SpringBootTest
public class UpbitOrderTest {

    @Autowired
    Order upbitOrder;

    @Autowired
    Order simulateUpbitOrder;

    @DisplayName("업비트 주문하기 하고 취소하기")
    @Test
    void upbitOrderTest() {
        final String market = "KRW-BTC";
        final TradeType tradeType = TradeType.BUY;
        final Integer cash = 5100;
        final Double price = 5100.0;
        OrderRequest request = UpbitOrderRequest
                .builder("tjdfhrdk10@naver.com", market, tradeType)
                .cash(cash)
                .price(price)
                .build();
        OrderResponse response = upbitOrder.order(request);
        assertEquals(market, response.getMarket());

        // 취소하기
        String uuid = response.getUuid();
        OrderCancelRequest cancelRequest = UpbitOrderCancelRequest.builder()
                .client("tjdfhrdk10@naver.com")
                .uuid(uuid)
                .build();

        OrderResponse cancelResponse = upbitOrder.cancelOrder(cancelRequest);
        assertEquals(uuid, cancelResponse.getUuid());
    }

    @DisplayName("가상 주문 하기")
    @Test
    void simulateUpbitOrderTest() {
        final String market = "KRW-BTC";
        final TradeType tradeType = TradeType.BUY;
        final Integer cash = 5100;
        final Double buyPrice = 510.0;

        final String client = "million";
        OrderRequest buyRequest = UpbitOrderRequest.builder(client, market, tradeType)
                .orderDate(LocalDateTime.now())
                .cash(cash)
                .price(buyPrice)
                .build();
        OrderResponse buyResponse = simulateUpbitOrder.order(buyRequest);
        OrderListRequest orderListRequest = buyRequest.toOrderListRequest();
        OrderResponses orderResponses = simulateUpbitOrder.orderList(orderListRequest);
        assertEquals(market, buyResponse.getMarket());
        assertEquals(1, orderResponses.size());

        final TradeType sellTradeType = TradeType.SELL;
        final Double sellPrice = 530.0;

        OrderRequest sellRequest = UpbitOrderRequest.builder(client, market, sellTradeType)
                .price(sellPrice)
                .orderDate(LocalDateTime.now())
                .volume(cash / buyPrice).build();

        simulateUpbitOrder.order(sellRequest);
        OrderListRequest listRequest = sellRequest.toOrderListRequest();
        assertEquals(2, simulateUpbitOrder.orderList(listRequest).size());
    }


}
