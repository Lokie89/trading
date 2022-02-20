package com.trading.chart.repository.chart;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trading.chart.application.chart.request.ChartRequest;
import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.domain.chart.UpbitChart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.trading.chart.domain.chart.QUpbitChart.upbitChart;

/**
 * @author SeongRok.Oh
 * @since 2022/02/19
 */
@RequiredArgsConstructor
@Repository
public class UpbitChartRepositorySupport implements ChartRepositorySupport {

    private final JPAQueryFactory queryFactory;

    @Override
    public ChartResponses getChart(ChartRequest request) {
        ChartResponse[] chartResponses = request.forWorkIndex();
        List<UpbitChart> charts = queryFactory.select(upbitChart)
                .from(upbitChart)
                .where(
                        between(chartResponses[0].getTime(), chartResponses[1].getTime()),
                        eqMarket(request),
                        eqUnit(request)
                )
                .orderBy(upbitChart.time.asc())
                .fetch()
        ;
        return ChartResponses.of(charts.stream().map(UpbitChart::toDto).collect(Collectors.toList()));
    }

    private BooleanExpression between(LocalDateTime from, LocalDateTime to) {
        return upbitChart.time.between(from, to);
    }

    private BooleanExpression eqMarket(ChartRequest request) {
        return upbitChart.market.eq(request.getMarket());
    }

    private BooleanExpression eqUnit(ChartRequest request) {
        return upbitChart.unit.eq(request.getUnit());
    }

}
