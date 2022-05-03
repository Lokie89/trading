package com.trading.chart.application.candle.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpbitCandleRequest implements CandleRequest {

    @Getter
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
            url += "&to=" + ZonedDateTime.of(to, ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_INSTANT);
//            url += "&to=" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(to);
        }
        return url;
    }

    public static Builder builder(UpbitUnit unit, String market) {
        return new Builder(unit, market);
    }

    public static class Builder {
        private final UpbitUnit unit;
        private final String market;
        private Integer count = 1;
        private LocalDateTime to;

        public Builder(final UpbitUnit unit, final String market) {
            this.unit = unit;
            this.market = market;
        }

        public Builder count(final Integer count) {
            if (Objects.nonNull(count)) {
                this.count = count;
            }
            return this;
        }

        public Builder to(LocalDateTime to) {
            this.to = Objects.nonNull(to) ? to : LocalDateTime.now();
            return this;
        }

        public CandleRequest build() {
            return new UpbitCandleRequest(unit, count, market, to);
        }

    }
}
