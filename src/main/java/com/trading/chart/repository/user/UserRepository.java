package com.trading.chart.repository.user;

import com.trading.chart.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SeongRok.Oh
 * @since 2021/11/08
 */
public interface UserRepository extends JpaRepository<User,String> {
}
