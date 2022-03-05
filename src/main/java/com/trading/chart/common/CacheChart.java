package com.trading.chart.common;

import com.trading.chart.application.chart.response.ChartResponses;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
public class CacheChart extends ConcurrentHashMap<ChartKey, ChartResponses> {

    public void add(ChartKey key, ChartResponses value) {
        if (Objects.isNull(this.get(key))) {
            this.put(key, value);
            return;
        }
        this.get(key).add(value);
    }
}
