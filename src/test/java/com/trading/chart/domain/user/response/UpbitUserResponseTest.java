package com.trading.chart.domain.user.response;

import com.trading.chart.application.order.request.TradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertTrue(notSellingUser.isTradeStatus(notSellingUser.toTradeRequest(market, null, TradeType.BUY, null)));

        assertFalse((notSellingUser.isTradeStatus(notSellingUser.toTradeRequest(market, null, TradeType.SELL, null))));

    }

}
