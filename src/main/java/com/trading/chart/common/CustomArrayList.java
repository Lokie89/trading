package com.trading.chart.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public class CustomArrayList<T> extends ArrayList<T> {
    public CustomArrayList(List<T> list){
        super(list);
    }
    public T getReverse(int index) {
        return get(size() - index);
    }
}
