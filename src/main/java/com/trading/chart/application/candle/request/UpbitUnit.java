package com.trading.chart.application.candle.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public enum UpbitUnit {
    MINUTE_ONE(1, "minutes/1"),
    MINUTE_THREE(3, "minutes/3"),
    MINUTE_FIVE(5, "minutes/5"),
    MINUTE_TEN(10, "minutes/10"),
    MINUTE_FIFTEEN(15, "minutes/15"),
    MINUTE_THIRTY(30, "minutes/30"),
    MINUTE_SIXTY(60, "minutes/60"),
    MINUTE_TWOFORTY(240, "minutes/240"),
    DAY(1440, "days"),
    WEEK(10080, "weeks"),
    MONTH(43200, "months"),
    ;

    @Getter
    private int minute;
    private String uri;

    UpbitUnit(Integer minute, String uri) {
        this.minute = minute;
        this.uri = uri;
    }

    private static Map<Integer, UpbitUnit> unitMap = new HashMap<>();

    static {
        unitMap.put(MINUTE_ONE.minute, UpbitUnit.MINUTE_ONE);
        unitMap.put(MINUTE_THREE.minute, UpbitUnit.MINUTE_THREE);
        unitMap.put(MINUTE_FIVE.minute, UpbitUnit.MINUTE_FIVE);
        unitMap.put(MINUTE_TEN.minute, UpbitUnit.MINUTE_TEN);
        unitMap.put(MINUTE_FIFTEEN.minute, UpbitUnit.MINUTE_FIFTEEN);
        unitMap.put(MINUTE_THIRTY.minute, UpbitUnit.MINUTE_THIRTY);
        unitMap.put(MINUTE_SIXTY.minute, UpbitUnit.MINUTE_SIXTY);
        unitMap.put(MINUTE_TWOFORTY.minute, UpbitUnit.MINUTE_TWOFORTY);
        unitMap.put(DAY.minute, UpbitUnit.DAY);
        unitMap.put(WEEK.minute, UpbitUnit.WEEK);
        unitMap.put(MONTH.minute, UpbitUnit.MONTH);
    }

    public String getUrl() {
        return "https://api.upbit.com/v1/candles/" + uri;
    }

    public static UpbitUnit get(Integer minute) {
        return unitMap.get(minute);
    }

}
