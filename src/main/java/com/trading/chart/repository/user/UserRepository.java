package com.trading.chart.repository.user;

import com.trading.chart.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author SeongRok.Oh
 * @since 2022/03/05
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByClient(String client);
}
