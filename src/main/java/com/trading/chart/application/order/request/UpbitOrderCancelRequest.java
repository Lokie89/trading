package com.trading.chart.application.order.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UpbitOrderCancelRequest implements OrderRequest, Serializable {
    @Getter
    @JsonIgnore
    private final String account;
    @JsonProperty(value = "uuid")
    private final String uuid;
}
