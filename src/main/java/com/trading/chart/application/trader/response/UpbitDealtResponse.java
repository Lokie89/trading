package com.trading.chart.application.trader.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.trading.chart.application.order.request.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author SeongRok.Oh
 * @since 2021/11/16
 */

/**
 * market : 종목
 * trade_date_utc : 체결 일자(UTC 기준)
 * trade_time_utc : 체결 시각(UTC 기준)
 * timestamp : 체결 타임스탬프
 * trade_price : 체결 가격
 * trade_volume : 체결량
 * prev_closing_price : 전일 종가
 * change_price: 변화량
 * ask_bid : 매도/매수
 * sequential_id : 체결번호
 */
@Getter
public class UpbitDealtResponse implements DealtResponse {

    @ApiModelProperty
    private String market;

    @ApiModelProperty()
    @JsonProperty(value = "trade_date_utc")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate tradeDateUtc;

    @ApiModelProperty()
    @JsonProperty(value = "trade_time_utc")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime tradeTimeUtc;

    @ApiModelProperty
    private Long timestamp;

    @ApiModelProperty
    @JsonProperty(value = "trade_price")
    private Double tradePrice;

    @ApiModelProperty
    @JsonProperty(value = "trade_volume")
    private Double tradeVolume;

    @ApiModelProperty
    @JsonProperty(value = "prev_closing_price")
    private Double prevLastPrice;

    @ApiModelProperty
    @JsonProperty(value = "change_price")
    private Double changePrice;

    @ApiModelProperty
    @JsonProperty(value = "ask_bid")
    private TradeType tradeType;

    @ApiModelProperty
    @JsonProperty(value = "sequential_id")
    private Long sequentialId;


}
