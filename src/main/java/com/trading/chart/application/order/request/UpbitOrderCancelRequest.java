package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.chart.application.trader.request.AccountRequest;
import com.trading.chart.application.trader.request.UpbitAccountRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author SeongRok.Oh
 * @since 2021/11/13
 */
@ToString
@RequiredArgsConstructor
@Builder
public class UpbitOrderCancelRequest implements OrderCancelRequest {
    @Getter
    @JsonIgnore
    private final String client;
    @JsonProperty(value = "uuid")
    private final String uuid;

}
