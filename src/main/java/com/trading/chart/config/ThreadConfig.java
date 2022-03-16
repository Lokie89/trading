package com.trading.chart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author SeongRok.Oh
 * @since 2022/03/08
 */
// TODO : 다시 알아 보고 적용 CallAPI 의 Thread.sleep 이랑 연관이 있는듯
@Configuration
public class ThreadConfig {

    @Bean
    public Executor exchangeExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setThreadNamePrefix("exchange-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
