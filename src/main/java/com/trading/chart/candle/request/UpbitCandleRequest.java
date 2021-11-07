package com.trading.chart.candle.request;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@RequiredArgsConstructor
public class UpbitCandleRequest implements CandleRequest {

    private final UpbitUnit unit;
    private final Integer count;
    private final String market;
    private final LocalDateTime to;

    @Override
    public String getUrl() {
        String url = unit.getUrl() + "?market=" + market;
        if (Objects.nonNull(count) && count > 0) {
            url += "&count=" + count;
        }
        if (Objects.nonNull(to) && LocalDateTime.now().isAfter(to)) {
            url += "&to=" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(to);
        }
        return url;
    }

    public static Builder builder(UpbitUnit unit, String market) {
        return new Builder(unit, market);
    }

    public static class Builder {
        private final UpbitUnit unit;
        private Integer count;
        private String market;
        private LocalDateTime to;

        public Builder(final UpbitUnit unit, final String market) {
            this.unit = unit;
            this.market = market;
        }

        public Builder count(final Integer count) {
            this.count = count;
            return this;
        }

        public Builder lastTime(LocalDateTime lastTime) {
            this.to = lastTime;
            return this;
        }

        public UpbitCandleRequest build() {
            return new UpbitCandleRequest(unit, count, market, to);
        }
    }
}
