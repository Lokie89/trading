package com.trading.chart.application.chart.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
public enum LinePeriod {
    THREE(3),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    SIXTY(60),
    ONETWENTY(120),
    TWOFORTY(240),
    ;

    @Getter
    private int period;

    LinePeriod(int period) {
        this.period = period;
    }

    private static Map<Integer, LinePeriod> periodMap = new HashMap<>();

    static {
        periodMap.put(THREE.period, LinePeriod.THREE);
        periodMap.put(FIVE.period, LinePeriod.FIVE);
        periodMap.put(TEN.period, LinePeriod.TEN);
        periodMap.put(TWENTY.period, LinePeriod.TWENTY);
        periodMap.put(SIXTY.period, LinePeriod.SIXTY);
        periodMap.put(ONETWENTY.period, LinePeriod.ONETWENTY);
        periodMap.put(TWOFORTY.period, LinePeriod.TWOFORTY);
    }

    public static Optional<LinePeriod> get(final Integer period) {
        return Optional.of(periodMap.get(period));
    }

}
