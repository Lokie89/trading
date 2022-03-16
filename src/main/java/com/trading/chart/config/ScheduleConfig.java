package com.trading.chart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author SeongRok.Oh
 * @since 2022/04/07
 */
@Profile("!test")
@EnableScheduling
@Configuration
public class ScheduleConfig {
}
