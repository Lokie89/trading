package com.trading.chart.common;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
@NoArgsConstructor
public class DeformedList<E> extends ArrayList<E> {
    public DeformedList(List<E> list) {
        super(list);
    }

    public static <E> DeformedList<E> of(List<E> list){
        return new DeformedList<>(list);
    }

    public DeformedList<E> copy(int fromIndex, int toIndex) {
        return new DeformedList<>(subList(fromIndex, toIndex));
    }

    public DeformedList<E> copyReverse(int fromIndex, int toIndex) {
        DeformedList<E> copyReverse
                = new DeformedList<>(subList(size() - toIndex, size() - fromIndex));
        Collections.reverse(copyReverse);
        return copyReverse;
    }

    public E getReverse(int index) {
        return get(size() - 1 - index);
    }
}
