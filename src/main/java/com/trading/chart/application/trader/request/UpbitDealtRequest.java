package com.trading.chart.application.trader.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/16
 */
@AllArgsConstructor
@Getter
public class UpbitDealtRequest implements DealtRequest {
    private String client;
    private String market;
    private LocalDateTime to;
    private Integer count;
    private Integer daysAgo;

    public static Builder builder(final String client, final String market) {
        return new Builder(client, market);
    }

    public static class Builder {
        private final String client;
        private final String market;
        private LocalDateTime to;
        private Integer count;
        private Integer daysAgo;

        public Builder(final String client, final String market) {
            this.client = client;
            this.market = market;
        }

        public Builder lastTime(LocalDateTime lastTime) {
            this.to = lastTime;
            return this;
        }

        public Builder count(Integer count) {
            this.count = count;
            return this;
        }

        public Builder daysAgo(Integer daysAgo) {
            this.daysAgo = daysAgo;
            return this;
        }

        public UpbitDealtRequest build() {
            return new UpbitDealtRequest(this.client, this.market, this.to, this.count, this.daysAgo);
        }
    }
}
