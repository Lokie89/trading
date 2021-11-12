package com.trading.chart.application.order.request;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/11/12
 */
@Getter
public enum UpbitOrderSort {
    ASC("asc"),
    DESC("desc"),
    ;

    private String upbitOrderSort;

    UpbitOrderSort(String upbitOrderSort) {
        this.upbitOrderSort = upbitOrderSort;
    }
}
