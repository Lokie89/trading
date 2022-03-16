package com.trading.chart.repository;

import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.User;
import com.trading.chart.repository.user.UpbitUserRepository;
import com.trading.chart.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2022/02/17
 */
@DisplayName("업비트 유저 데이터 테스트")
@SpringBootTest
public class UpbitUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UpbitUserRepository upbitUserRepository;

    @DisplayName("유저 조회 테스트")
    @Test
    void findUserTest() {
        final String client = "Traeuman";
        Optional<User> user = userRepository.findByClient(client);
        user.ifPresent(value -> assertEquals(client, value.getClient()));
    }

    @DisplayName("업비트 유저 조회 테스트")
    @Test
    void findUpbitUserTest() {
        final String client = "Traeuman";
        Optional<UpbitUser> userOptional = upbitUserRepository.findByUpbitClient("tjdfhrdk10@naver.com");
        userOptional.ifPresent(value -> assertEquals(client, value.getUser().getClient()));
    }

//    @DisplayName("계정 init")
//    @Test
//    void insertUpbitUser() {
//        User user = User.builder().client("Traeuman").password("1111").build();
//        User savedUser = userRepository.save(user);
//        UpbitUser upbitUser = UpbitUser.builder().user(savedUser).upbitClient("tjdfhrdk10@naver.com")
//                .upbitAccessKey("NEFu0iUDnOQn9634RxU4gjBTR27eNl1nQZD0T3NI")
//                .upbitSecretKey("muDaq6tnYSSTmtR2PxhWe57yNTf7PcEGuVxEwcRT")
//                .tradeResources(List.of(TradeResourceResponse.builder(ExchangePlatform.UPBIT, TradeType.BUY, TradeStrategy.LOWER_BOLLINGERBANDS, UpbitUnit.DAY)
//                        .build().toEntity()))
//                .cashAtOnce(5000)
//                .buyLimit(100000)
//                .isBuying(true)
//                .isSelling(true)
//                .build();
//
//        UpbitUser savedUpbitUser = upbitUserRepository.save(upbitUser);
//        assertEquals(savedUpbitUser.getUser(), savedUser);
//    }
}
