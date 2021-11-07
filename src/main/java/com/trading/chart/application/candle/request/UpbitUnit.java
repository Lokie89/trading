package com.trading.chart.application.candle.request;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
public enum UpbitUnit {
    MINUTE_ONE("minutes/1"),
    MINUTE_THREE("minutes/3"),
    MINUTE_FIVE("minutes/5"),
    MINUTE_TEN("minutes/10"),
    MINUTE_FIFTEEN("minutes/15"),
    MINUTE_THIRTY("minutes/30"),
    MINUTE_SIXTY("minutes/60"),
    MINUTE_TWOFORTY("minutes/240"),
    DAY("days"),
    WEEK("weeks"),
    MONTH("months"),
    ;

    private String uri;

    UpbitUnit(String uri) {
        this.uri = uri;
    }

    public String getUrl(){
        return "https://api.upbit.com/v1/candles/" + uri;
    }

}
