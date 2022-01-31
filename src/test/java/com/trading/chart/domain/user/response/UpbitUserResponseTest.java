package com.trading.chart.domain.user.response;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.application.trader.response.UpbitAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SeongRok.Oh
 * @since 2022/01/29
 */
@DisplayName("업비트 유저 테스트")
@SpringBootTest
public class UpbitUserResponseTest {

    @DisplayName("주문에 대한 User 거래여부 판단")
    @Test
    void tradeStatusTest() {
        final String client = "anonymous";
        final String market = "COIN";
        UpbitUserResponse notSellingUser = UpbitUserResponse.builder().id(client).buying(true).selling(false).build();

        OrderRequest buyOrderRequest = UpbitOrderRequest.builder(client, market, TradeType.BUY).build();
        assertTrue(notSellingUser.isTradeStatus(buyOrderRequest));

        OrderRequest sellOrderRequest = UpbitOrderRequest.builder(client, market, TradeType.SELL).volume(50.0).build();
        assertFalse((notSellingUser.isTradeStatus(sellOrderRequest)));

    }

    @DisplayName("주문에 대한 User 의 거래가능 판단")
    @Test
    void availableTradeTest() {

        // 총 55000원 매수, 남은 금액 25000원
        AccountResponses accounts = AccountResponses.of(
                UpbitAccount.of("KRW", 25000.0, null),
                UpbitAccount.of("ABC", 1000.0, 20.0), // 20000
                UpbitAccount.of("DEF", 20000.0, 1.0), // 20000
                UpbitAccount.of("GGG", 15000.0, 1.0)  // 15000
        );

        final String client = "anonymous";
        final String market = "COIN";
        final Integer cash = 12000;

        UpbitUserResponse buyLimiter = UpbitUserResponse.builder().id(client).buyLimit(50000).accounts(accounts).build();

        UpbitUserResponse lackBuyer = UpbitUserResponse.builder().id(client).cashAtOnce(30000).accounts(accounts).build();

        OrderRequest buyLimiterOrderRequest = UpbitOrderRequest.builder(client, market, TradeType.BUY).build();
        OrderRequest lackBuyerOrderRequest = UpbitOrderRequest.builder(client, market, TradeType.BUY).cash(cash).build();
        assertFalse(buyLimiter.isAvailableTrade(buyLimiterOrderRequest));
        assertFalse(lackBuyer.isAvailableTrade(lackBuyerOrderRequest));
    }
}
