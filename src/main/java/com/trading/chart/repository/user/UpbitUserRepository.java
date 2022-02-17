package com.trading.chart.repository.user;

import com.trading.chart.domain.user.UpbitUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author SeongRok.Oh
 * @since 2021/11/08
 */
public interface UpbitUserRepository extends JpaRepository<UpbitUser, String> {
    Optional<UpbitUser> findByClient(String client);
}
