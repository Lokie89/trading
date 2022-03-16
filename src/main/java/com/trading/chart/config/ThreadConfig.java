package com.trading.chart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author SeongRok.Oh
 * @since 2022/03/08
 */
@Configuration
public class ThreadConfig {

    @Bean
    public Executor exchangeExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setThreadNamePrefix("subscribe-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
