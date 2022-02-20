package com.trading.chart.repository.chart;

import com.trading.chart.domain.chart.UpbitChart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SeongRok.Oh
 * @since 2022/02/17
 */
public interface UpbitChartRepository extends JpaRepository<UpbitChart, Long> {
}
