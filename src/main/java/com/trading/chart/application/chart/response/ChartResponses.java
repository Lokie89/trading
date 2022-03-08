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

    public void addAll(ChartResponses chartResponses) {
        if (Objects.nonNull(chartResponses) && chartResponses.isNotEmpty()) {
            // TODO : 기존에 있는 로우들을 리턴 값에 합쳐 보내기 위함 - Set 에는 대체 하는 로직이 메서드가 없나?
            this.chartResponses.removeAll(chartResponses.chartResponses);
            this.chartResponses.addAll(chartResponses.chartResponses);
        }
    }

    public ChartResponses substitute(ChartResponse includeFrom, ChartResponse excludeTo) {
        return new ChartResponses(this.chartResponses.subSet(includeFrom, excludeTo));
    }

    public ChartResponses substituteTo(ChartResponse includeTo) {
        return new ChartResponses(this.chartResponses.headSet(includeTo));
    }

    public ChartResponses substituteFrom(ChartResponse includeFrom) {
        return new ChartResponses(this.chartResponses.tailSet(includeFrom));
    }

    public Spliterator<ChartResponse> spliterator() {
        return chartResponses.spliterator();
    }

    public Stream<ChartResponse> stream() {
        return chartResponses.stream();
    }

    public boolean isNotSatisfied(ChartRequest request) {
        final int mandatoryCount = request.getMandatoryCount();
        ChartResponse[] fromTo = request.forWorkIndex();
        return substitute(fromTo[0], fromTo[1]).size() < mandatoryCount;
    }

    public boolean isNotEmpty() {
        return !this.chartResponses.isEmpty();
    }
}
