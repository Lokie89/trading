package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                                 final UpbitOrderState state,
                                 final List<UpbitOrderState> states,
                                 final Integer page,
                                 final Integer limit,
                                 final UpbitOrderSort orderBy) {
        this.account = account;
        this.market = market;
        this.uuids = uuids;
        this.identifiers = identifiers;
        this.state = Objects.nonNull(state) ? state.getState() : null;
        this.states = Objects.nonNull(states) ? states.stream()
                .map(UpbitOrderState::getState)
                .collect(Collectors.toList()) : null;
        this.page = page;
        this.limit = limit;
        this.orderBy = Objects.nonNull(orderBy) ? orderBy.getUpbitOrderSort() : null;
    }
}
