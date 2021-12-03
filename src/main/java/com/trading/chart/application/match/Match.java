package com.trading.chart.application.match;

import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.match.response.MatchResponse;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
public interface Match {
    MatchResponse match(MatchRequest request);
}
