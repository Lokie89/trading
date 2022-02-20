package com.trading.chart.application.chart.response;

import com.trading.chart.application.chart.request.ChartRequest;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author SeongRok.Oh
 * @since 2021/11/19
 */
public class ChartResponses {

    private final SortedSet<ChartResponse> chartResponses;

    private ChartResponses(Collection<ChartResponse> chartCollection) {
        this.chartResponses = new TreeSet<>(Comparator.comparing(ChartResponse::getTime));
        this.chartResponses.addAll(chartCollection);
    }

    public static ChartResponses of(Collection<ChartResponse> chartCollection) {
        return new ChartResponses(chartCollection);
    }

    public int size() {
        return this.chartResponses.size();
    }

    public ChartResponse getLast() {
        return this.chartResponses.last();
    }

    public void add(ChartResponses chartResponses) {
        this.chartResponses.addAll(chartResponses.chartResponses);
    }

    public ChartResponses substitute(ChartResponse includeFrom, ChartResponse excludeTo) {
        return new ChartResponses(this.chartResponses.subSet(includeFrom, excludeTo));
    }

    public Spliterator<ChartResponse> spliterator() {
        return chartResponses.spliterator();
    }

    public Stream<ChartResponse> stream() {
        return chartResponses.stream();
    }

    public boolean isSatisfied(ChartRequest request){
        final int mandatoryCount = request.getMandatoryCount();
        ChartResponse[] fromTo = request.forWorkIndex();
        return substitute(fromTo[0], fromTo[1]).size() >= mandatoryCount;
    }
}
