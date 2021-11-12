package com.trading.chart.application.order;

import com.trading.chart.application.order.request.*;
import com.trading.chart.application.order.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        final String market = "KRW-BTT";
        final TradeType tradeType = TradeType.BUY;
        final Integer cash = 5100;
        final Double price = 3.5;
        OrderRequest request = UpbitOrderRequest.builder().account("tjdfhrdk10@naver.com").item(market).tradeType(tradeType).cash(cash).price(price).build();
        OrderResponse response = upbitOrder.order(request);
        assertEquals(market, response.getMarket());
    }

    @DisplayName("업비트 주문내역 보기")
    @Test
    void getUpbitOrderListTest() {
        final String market = "KRW-BTT";
        final UpbitOrderState state = UpbitOrderState.DONE;
        OrderRequest request = UpbitOrderListRequest.builder().account("tjdfhrdk10@naver.com").state(state).build();
        List<OrderResponse> responses = upbitOrder.getOrderList(request);
        assertTrue(responses.stream()
                .allMatch(orderResponse -> orderResponse.getMarket().equals(market)));
        assertTrue(responses.stream()
                .allMatch(orderResponse -> orderResponse.getState().equals(state)));
    }
}
