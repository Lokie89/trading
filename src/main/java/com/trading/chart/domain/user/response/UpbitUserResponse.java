package com.trading.chart.domain.user.response;

import com.trading.chart.application.order.request.OrderRequest;
import com.trading.chart.application.trader.response.AccountResponses;

import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2022/01/29
 */
public class UpbitUserResponse {

    private final String id;
    private final Boolean isBuying;
    private final Integer buyLimit;
    private final Integer cashAtOnce;
    private final Boolean isSelling;
    private final AccountResponses upbitAccounts;

    private UpbitUserResponse(String id, Boolean isBuying, Integer buyLimit,
                              Integer cashAtOnce, Boolean isSelling, AccountResponses upbitAccounts) {
        this.id = id;
        this.isBuying = isBuying;
        this.buyLimit = buyLimit;
        this.cashAtOnce = cashAtOnce;
        this.isSelling = isSelling;
        this.upbitAccounts = upbitAccounts;
    }

    // 해당 계정이 isBuying or isSelling 이 false 일 때
    public boolean isTradeStatus(OrderRequest orderRequest) {
        return (orderRequest.isBuyOrder() && isBuying) || (!orderRequest.isBuyOrder() && isSelling);
    }

    // 해당 계정의 buyingLimit 가 남았는지, cashAtOnce 이상 여유가 있는지
    public boolean isAvailableTrade(OrderRequest orderRequest) {
        boolean remainCash = upbitAccounts.remainCash(cashAtOnce);
        upbitAccounts.apply(orderRequest);
        return upbitAccounts.usedCash() <= buyLimit && remainCash;
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
        private AccountResponses upbitAccounts;

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

        public Builder accounts(AccountResponses upbitAccounts) {
            if (Objects.nonNull(upbitAccounts)) {
                this.upbitAccounts = upbitAccounts;
            }
            return this;
        }

        public UpbitUserResponse build() {
            return new UpbitUserResponse(this.id, this.isBuying, this.buyLimit,
                    this.cashAtOnce, this.isSelling, this.upbitAccounts);
        }
    }

}
