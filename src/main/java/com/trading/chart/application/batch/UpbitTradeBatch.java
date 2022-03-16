package com.trading.chart.application.batch;

import com.trading.chart.application.exchange.Exchange;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.repository.user.UpbitUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/03/05
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitTradeBatch {

    private final UpbitUserRepository repository;
    private final Exchange upbitExchange;

//    @Scheduled(cron = "20 0/1 * * * *")
    public void autoTrade() {
        List<UpbitUser> upbitUsers = repository.findAll();
        for (UpbitUser upbitUser : upbitUsers) {
            upbitExchange.exchange(upbitUser.toUserResponse(), LocalDateTime.now());
        }
    }
}
