package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/11
 */

@Getter
public class UpbitOrderListRequest implements OrderRequest {
    @JsonIgnore
    private final String account;
    private final String market;
    private final List<String> uuids;
    private final List<String> identifiers;
    private final String state;
    private final List<String> states;
    private final Integer page;
    private final Integer limit;
    @JsonProperty("order_by")
    private final String orderBy;

    @Builder
    public UpbitOrderListRequest(final String account,
                                 final String market,
                                 final List<String> uuids,
                                 final List<String> identifiers,
                                 final String state,
                                 final List<String> states,
                                 final Integer page,
                                 final Integer limit,
                                 final String orderBy) {
        this.account = account;
        this.market = market;
        this.uuids = uuids;
        this.identifiers = identifiers;
        this.state = state;
        this.states = states;
        this.page = page;
        this.limit = limit;
        this.orderBy = orderBy;
    }
}
