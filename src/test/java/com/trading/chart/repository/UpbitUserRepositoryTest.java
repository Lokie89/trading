package com.trading.chart.repository;

import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.repository.user.UpbitUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2022/02/17
 */
@DisplayName("업비트 유저 데이터 테스트")
@SpringBootTest
public class UpbitUserRepositoryTest {

    @Autowired
    UpbitUserRepository userRepository;

    @DisplayName("유저 조회 테스트")
    @Test
    void findUserTest() {
        final String client = "Traeuman";
        UpbitUser upbitUser = userRepository.findByClient(client).orElse(null);
        assertEquals(client, upbitUser.getClient());
    }
}
