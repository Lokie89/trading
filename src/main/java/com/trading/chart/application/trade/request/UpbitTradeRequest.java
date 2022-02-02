package com.trading.chart.application.trade.request;

import com.trading.chart.application.match.request.MatchRequest;
import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import com.trading.chart.domain.user.response.UpbitTradeResourceResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/01/25
 */
public class UpbitTradeRequest implements TradeRequest {

    private final String client;
    private final TradeType side;
    private final Double price;
    private final Double volume;

    private final String market;
    private final LocalDateTime date;
    private final List<UpbitTradeResourceResponse> tradeResources;


    private UpbitTradeRequest(String client, TradeType side,
                              Double price, Double volume,
                              String market, LocalDateTime date,
                              List<UpbitTradeResourceResponse> tradeResources) {
        this.client = client;
        this.side = side;
        this.price = price;
        this.volume = volume;
        this.market = market;
        this.date = date;
        this.tradeResources = tradeResources;
    }

    public static Builder builder(String client, TradeType side, String market) {
        return new Builder(client, side, market);
    }

    @Override
    public List<MatchRequest> toMatchRequests() {
        return tradeResources.stream()
                .map(tradeResource -> tradeResource.toUpbitMatchRequest(market, date))
                .collect(Collectors.toList());
    }

    @Override
    public OrderRequest toOrderRequest() {
        return UpbitOrderRequest.builder(client, market, side)
                .price(price)
                .volume(volume)
                .build();
    }

    public static class Builder {
        private final String client;
        private final TradeType side;
        private Double price;
        private Double volume;

        private final String market;
        private LocalDateTime date = LocalDateTime.now();
        private List<UpbitTradeResourceResponse> tradeResources = new ArrayList<>();


        private Builder(String client, TradeType side,
                        String market) {
            this.client = client;
            this.side = side;
            this.market = market;
        }

        public Builder date(LocalDateTime date) {
            if (Objects.nonNull(date)) {
                this.date = date;
            }
            return this;
        }

        public Builder price(Double price) {
            if (Objects.nonNull(price)) {
                this.price = price;
            }
            return this;
        }

        public Builder volume(Double volume) {
            if (Objects.nonNull(volume)) {
                this.volume = volume;
            }
            return this;
        }

        public Builder tradeResources(List<UpbitTradeResourceResponse> tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = tradeResources;
            }
            return this;
        }

        public Builder tradeResources(UpbitTradeResourceResponse... tradeResources) {
            if (Objects.nonNull(tradeResources)) {
                this.tradeResources = Arrays.asList(tradeResources);
            }
            return this;
        }

        public UpbitTradeRequest build() {
            return new UpbitTradeRequest(this.client, this.side, this.price, this.volume,
                    this.market, this.date, this.tradeResources);
        }
    }
}
