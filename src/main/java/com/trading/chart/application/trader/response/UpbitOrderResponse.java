package com.trading.chart.application.trader.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpbitOrderResponse implements OrderResponse {

    private String uuid;
    private String side;
    @JsonProperty("ord_Type")
    private String orderType;
    private Double price;
    private String state;
    private String market;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    private Double volume;
    @JsonProperty("remaining_volume")
    private Double remainingVolume;
    @JsonProperty("reversed_fee")
    private Double reversedFee;
    @JsonProperty("remaining_fee")
    private Double remainingFee;
    @JsonProperty("paid_fee")
    private Double paidFee;
    private Double locked;
    @JsonProperty("executed_volume")
    private Double executedVolume;
    @JsonProperty("trade_count")
    private Double tradeCount;
}
