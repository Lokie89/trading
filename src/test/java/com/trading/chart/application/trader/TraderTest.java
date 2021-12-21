package com.trading.chart.application.trader;

import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import com.trading.chart.application.trader.response.AccountResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@DisplayName("거래자 정보 테스트")
@SpringBootTest
public class TraderTest {

    @Autowired
    Trader upbitTrader;

    @Autowired
    Trader simulateUpbitTrader;

    @DisplayName("남아있는 금액 조회")
    @Test
    void getBalanceTest() {
        final String client = "tjdfhrdk10@naver.com";
        AccountRequest accountRequest = UpbitAccountRequest.builder(client)
                .build();
        assertTrue(upbitTrader.getAccounts(accountRequest).stream()
                .anyMatch(AccountResponse::isOwn));
    }

//    @DisplayName("최근 체결 내역 조회")
//    @Test
//    void getRecentlyDealtTest() {
//
//        final String market = "KRW-BTT";
//        DealtRequest dealtRequest = UpbitDealtRequest.builder("tjdfhrdk10@naver.com", market).build();
//        assertTrue(upbitTrader.getRecentlyDealt(dealtRequest).stream()
//                .allMatch(dealt -> market.equals(dealt.getMarket())));
//    }

    @DisplayName("가상 계정 남아있는 금액 조회")
    @Test
    void getSimulatorBalanceTest() {
        final String client = "tjdfhrdk10@naver.com";
        AccountRequest accountRequest = UpbitAccountRequest.builder(client)
                .build();
        assertTrue(simulateUpbitTrader.getAccounts(accountRequest).stream()
                .anyMatch(AccountResponse::isOwn));
    }

}
