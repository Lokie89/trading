package com.trading.chart.application.trader.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/12/21
 */
@EqualsAndHashCode(of = "client")
public class UpbitAccountRequest implements AccountRequest {
    @Getter
    private final String client;

    private UpbitAccountRequest(String client) {
        this.client = client;
    }

    public static Builder builder(String client) {
        return new Builder(client);
    }

    public static UpbitAccountRequest of(String client) {
        return new UpbitAccountRequest(client);
    }

    public static class Builder {

        private final String client;

        private Builder(String client) {
            this.client = client;
        }

        public UpbitAccountRequest build() {
            return new UpbitAccountRequest(client);
        }
    }
}
