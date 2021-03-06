package com.trading.chart.application.chart.request;

import com.trading.chart.application.candle.request.UpbitUnit;
import com.trading.chart.common.ChartKey;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@Builder
@EqualsAndHashCode(of = {"market", "unit"})
@RequiredArgsConstructor
public class UpbitKeyPoint implements ChartKey {
    private final String market;
    private final UpbitUnit unit;
}
