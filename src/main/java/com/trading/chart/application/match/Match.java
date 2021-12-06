package com.trading.chart.application.match;

import com.trading.chart.application.match.request.MatchRequest;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
public interface Match {
    boolean match(MatchRequest request);
}
