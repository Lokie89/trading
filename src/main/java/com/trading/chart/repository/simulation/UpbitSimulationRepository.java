package com.trading.chart.repository.simulation;

import com.trading.chart.domain.simulation.SimulateStatus;
import com.trading.chart.domain.simulation.UpbitSimulation;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/04/13
 */
public interface UpbitSimulationRepository extends JpaRepository<UpbitSimulation, Long> {
    List<UpbitSimulation> findAllByStatus(SimulateStatus status);
    UpbitSimulation findFirstByUserOrderByRequestDateDesc(UpbitUser user);
}
