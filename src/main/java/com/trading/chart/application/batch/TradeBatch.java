package com.trading.chart.application.batch;

import com.trading.chart.application.trader.Trader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/03/05
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class TradeBatch {
    private final Trader upbitTrader;

}
