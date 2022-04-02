package com.trading.chart.application.candle.request;

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
public class UpbitCandleRequest implements CandleRequest {

    @Getter
    private final UpbitUnit unit;
    private final Integer count;
    private final String market;
    private final LocalDateTime to;

    private UpbitCandleRequest(UpbitUnit unit, Integer count, String market, LocalDateTime to) {
        this.unit = unit;
        this.count = count;
        this.market = market;
        this.to = to;
    }

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
        private static final Integer COUNT_LIMIT = 200;

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

        public Builder lastTime(LocalDateTime lastTime) {
            this.to = Objects.nonNull(lastTime) ? lastTime : LocalDateTime.now();
            return this;
        }

        public List<CandleRequest> build() {
            List<CandleRequest> requestList = new ArrayList<>();

            do {
                if (count < COUNT_LIMIT) {
                    requestList.add(new UpbitCandleRequest(unit, count, market, to));
                    break;
                }
                count -= COUNT_LIMIT;
                requestList.add(new UpbitCandleRequest(unit, COUNT_LIMIT, market, goBack(count)));
            } while (count > 0);

            return requestList;
        }

        private LocalDateTime goBack(int count) {
            return to.minusMinutes((long) unit.getMinute() * count);
        }
    }
}
