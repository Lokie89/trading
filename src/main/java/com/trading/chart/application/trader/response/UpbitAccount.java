package com.trading.chart.application.trader.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */

/**
 * currency : 화폐를 의미하는 영문 대문자 코드
 * balance : 주문가능 금액/수량
 * locked : 주문 중 묶여있는 금액/수량
 * avg_buy_price : 매수평균가
 * avg_buy_price_modified : 매수평균가 수정 여부
 * unit_currency : 평단가 기준 화폐
 */
@EqualsAndHashCode(of = "currency")
@NoArgsConstructor
@Getter
public class UpbitAccount implements AccountResponse {

    @ApiModelProperty(value = "화폐", example = "KRW")
    @JsonProperty(value = "currency")
    private String currency;

    @ApiModelProperty(value = "주문가능 금액/수량", example = "1000000.0")
    @JsonProperty(value = "balance")
    private Double balance;

    @ApiModelProperty(value = "묶인 금액/수량", example = "0.0")
    @JsonProperty(value = "locked")
    private Double locked;

    @ApiModelProperty(value = "매수평균", example = "101000")
    @JsonProperty("avg_buy_price")
    private Double avgBuyPrice;

    @ApiModelProperty(value = "매수가수정여부", example = "false")
    @JsonProperty("avg_buy_price_modified")
    private Boolean avgBuyPriceModified;

    @ApiModelProperty(value = "평균 기준 화폐", example = "KRW")
    @JsonProperty("unit_currency")
    private String unitCurrency;


    private UpbitAccount(String currency, Double balance, Double avgBuyPrice) {
        this.currency = currency;
        this.balance = balance;
        this.avgBuyPrice = avgBuyPrice;
    }

    public static UpbitAccount of(String currency, Double volume, Double price) {
        return new UpbitAccount(currency, volume, price);
    }

    @Override
    public boolean isOwn() {
        return balance > 0;
    }

    @Override
    public void sellBalance(Double balance) {
        this.balance -= balance;
    }

    @Override
    public void buyBalance(Double balance) {
        this.balance += balance;
    }

}
