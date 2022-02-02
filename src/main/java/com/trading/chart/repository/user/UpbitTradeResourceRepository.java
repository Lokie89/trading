package com.trading.chart.repository.user;

import com.trading.chart.domain.user.UpbitTradeResource;
import com.trading.chart.domain.user.UpbitUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SeongRok.Oh
 * @since 2021/11/08
 */
public interface UpbitTradeResourceRepository extends JpaRepository<UpbitTradeResource, Long> {
}
