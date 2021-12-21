package com.trading.chart.application.order;

import com.trading.chart.application.order.request.*;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.order.response.UpbitOrderResponse;
import com.trading.chart.application.trader.Trader;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponses;
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
    Order<UpbitOrderResponse> upbitOrder;

    @Autowired
    Order<UpbitOrderResponse> simulateUpbitOrder;

    @Autowired
    Trader simulateUpbitTrader;

    @DisplayName("업비트 주문하기 하고 취소하기")
    @Test
    void upbitOrderTest() {
        final String market = "KRW-BTT";
        final TradeType tradeType = TradeType.BUY;
        final Integer cash = 5100;
        final Double price = 1.5;
        OrderRequest request = UpbitOrderRequest
                .builder("tjdfhrdk10@naver.com", market, tradeType)
                .cash(cash)
                .price(price)
                .build();
        OrderResponse response = upbitOrder.order(request);
        assertEquals(market, response.getMarket());

        // 취소하기
        String uuid = response.getUuid();
        OrderRequest cancelRequest = UpbitOrderCancelRequest.builder()
                .client("tjdfhrdk10@naver.com")
                .uuid(uuid)
                .build();

        OrderResponse cancelResponse = upbitOrder.cancelOrder(cancelRequest);
        assertEquals(uuid, cancelResponse.getUuid());
    }

//    @DisplayName("업비트 주문내역 보기")
//    @Test
//    void getUpbitOrderListTest() {
//        final String market = "KRW-BTT";
//        final UpbitOrderState state = UpbitOrderState.DONE;
//        OrderRequest request = UpbitOrderListRequest.builder()
//                .client("tjdfhrdk10@naver.com")
//                .state(state)
//                .market(market)
//                .build();
//        List<UpbitOrderResponse> responses = upbitOrder.getOrderList(request);
//        assertTrue(responses.stream()
//                .allMatch(orderResponse -> orderResponse.getMarket().equals(market)));
//        assertTrue(responses.stream()
//                .allMatch(orderResponse -> orderResponse.getState().equals(state)));
//    }

    @DisplayName("가상 주문 하기")
    @Test
    void simulateUpbitOrderTest() {
        final String market = "KRW-BTT";
        final TradeType buyTradeType = TradeType.BUY;
        final Integer cash = 5100;
        final Double buyPrice = 1.5;

        final String client = "million";
        OrderRequest buyRequest = UpbitOrderRequest.builder(client, market, buyTradeType)
                .cash(cash)
                .price(buyPrice)
                .build();
        OrderResponse buyResponse = simulateUpbitOrder.order(buyRequest);
        AccountRequest accountRequest = UpbitAccountRequest.builder(client)
                .build();
        AccountResponses accountResponses = simulateUpbitTrader.getAccounts(accountRequest);
        assertEquals(market, buyResponse.getMarket());
        assertEquals(2, accountResponses.size());

        final TradeType sellTradeType = TradeType.SELL;
        final Double sellPrice = 1.7;

        OrderRequest sellRequest = UpbitOrderRequest.builder(client, market, sellTradeType)
                .price(sellPrice)
                .volume(cash / buyPrice).build();

        simulateUpbitOrder.order(sellRequest);
        assertEquals(1, simulateUpbitOrder.getOrderList(sellRequest).size());
        assertEquals(1, accountResponses.size());
    }


}
