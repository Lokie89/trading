package com.trading.chart.application.trader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@DisplayName("거래자 정보 테스트")
@SpringBootTest
public class TraderTest {

    @Autowired
    Trader upbitTrader;

    @DisplayName("남아있는 금액 조회")
    @Test
    void getBalanceTest() {
        assertFalse(upbitTrader.getAccounts("tjdfhrdk10@naver.com").stream()
                .anyMatch(accountResponse -> accountResponse.getBalance() <= 0));
    }

    @DisplayName("거래한 내역 조회")
    @Test
    void getOrderTest(){
        upbitTrader.getOrders("tjdfhrdk10@naver.com");
    }
}
