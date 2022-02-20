package com.trading.chart.common;

import com.trading.chart.application.chart.response.ChartResponses;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
public class CacheChart extends HashMap<ChartKey, ChartResponses> {

    public void add(ChartKey key, ChartResponses value) {
        if (Objects.isNull(this.get(key))) {
            this.put(key, value);
            return;
        }
        this.get(key).add(value);
    }
}
