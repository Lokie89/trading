package com.trading.chart.domain.user.response;

import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.trade.request.TradeRequest;
import com.trading.chart.application.trade.request.UpbitTradeRequest;
import com.trading.chart.application.trader.response.AccountResponses;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/01/29
 */
public class UpbitUserResponse implements UserResponse {

    @Getter
    private final String id;
    private final Boolean isBuying;
    private final Integer buyLimit;
    private final Integer cashAtOnce;
    private final Boolean isSelling;
    private final AccountResponses accounts;
    private final List<UpbitTradeResourceResponse> tradeResources;

    private UpbitUserResponse(String id, Boolean isBuying, Integer buyLimit,
                              Integer cashAtOnce, Boolean isSelling,
                              AccountResponses accounts,
                              List<UpbitTradeResourceResponse> tradeResources) {
        this.id = id;
        this.isBuying = isBuying;
        this.buyLimit = buyLimit;
        this.cashAtOnce = cashAtOnce;
        this.isSelling = isSelling;
        this.accounts = accounts;
        this.tradeResources = tradeResources;
    }

    // 해당 계정이 isBuying or isSelling 이 false 일 때
    @Override
    public boolean isTradeStatus(TradeRequest tradeRequest) {
        return (tradeRequest.isBuyOrder() && isBuying) || (tradeRequest.isSellOrder() && isSelling);
    }

    @Override
    public boolean isLimited() {
        return buyLimit > (accounts.usedCash() + cashAtOnce);
    }

    @Override
    public TradeRequest toTradeRequest(String market, LocalDateTime date, TradeType tradeType, AccountResponses accounts) {
        return UpbitTradeRequest.builder(id, tradeType, market, accounts)
                .date(date)
                .tradeResources(tradeResources.stream()
                        .filter(tradeResource -> tradeResource.isEqualsTradeType(tradeType))
                        .collect(Collectors.toList()))
                .cash(cashAtOnce)
                .build();
    }

    @Override
    public boolean isAvailableTrade() {
        return accounts.isAffordable(toTradeRequest(null, null, TradeType.BUY, accounts));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private Integer cashAtOnce = 5000;
        private Boolean isBuying = true;
        private Boolean isSelling = true;
        private Integer buyLimit = Integer.MAX_VALUE;
        private AccountResponses accounts;
        private final List<UpbitTradeResourceResponse> tradeResources = new ArrayList<>();

        public Builder id(String id) {
            if (Objects.nonNull(id)) {
                this.id = id;
            }
            return this;
        }

        public Builder cashAtOnce(Integer cashAtOnce) {
            if (Objects.nonNull(cashAtOnce)) {
                this.cashAtOnce = cashAtOnce;
            }
            return this;
        }

        public Builder buying(Boolean isBuying) {
            if (Objects.nonNull(isBuying)) {
                this.isBuying = isBuying;
            }
            return this;
        }

        public Builder selling(Boolean isSelling) {
            if (Objects.nonNull(isSelling)) {
                this.isSelling = isSelling;
            }
            return this;
        }

        public Builder buyLimit(Integer buyLimit) {
            if (Objects.nonNull(buyLimit)) {
                this.buyLimit = buyLimit;
            }
            return this;
        }

        public Builder accounts(AccountResponses accounts) {
            if (Objects.nonNull(accounts)) {
                this.accounts = accounts;
            }
            return this;
        }

        public Builder tradeResources(UpbitTradeResourceResponse... resources) {
            if (Objects.nonNull(resources) && resources.length > 0) {
                this.tradeResources.addAll(Arrays.asList(resources));
            }
            return this;
        }

        public Builder tradeResources(List<UpbitTradeResourceResponse> resources) {
            if (Objects.nonNull(resources) && resources.size() > 0) {
                this.tradeResources.addAll(resources);
            }
            return this;
        }

        public UpbitUserResponse build() {
            return new UpbitUserResponse(this.id, this.isBuying, this.buyLimit, this.cashAtOnce,
                    this.isSelling, this.accounts, this.tradeResources);
        }
    }

}
