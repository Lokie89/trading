package com.trading.chart.application.match;

import com.trading.chart.application.match.request.MatchRequest;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
public interface Match {
    boolean match(List<MatchRequest> request);
}
