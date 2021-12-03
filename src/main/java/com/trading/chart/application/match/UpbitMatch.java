package com.trading.chart.application.match;

import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.match.response.MatchResponse;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/12/04
 */
@Component
public class UpbitMatch implements Match {
    @Override
    public MatchResponse match(MatchRequest request) {
        return null;
    }
}
