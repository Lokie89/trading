package com.trading.chart.application.order.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderState;
import com.trading.chart.domain.simulation.SimulatedOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * uuid : 주문의 고유 아이디
 * side : 주문 종류
 * ord_type : 주문 방식
 * price : 주문 화폐 가격
 * state : 주문 상태
 * market : 마켓 유일 키
 * created_at : 주문 생성 시간
 * volume : 사용자가 입력한 주문 양
 * remaining_volume : 체결 후 남은 주문양
 * paid_fee : 사용된 수수료
 * locked : 사용중인 비용
 * executed_volume : 체결된 거래량
 * trade_count : 주문에 걸린 체결 수
 */
@NoArgsConstructor
@Getter
public class UpbitOrderResponse implements OrderResponse {

    private String uuid;
    @JsonProperty("side")
    private TradeType side;
    @JsonProperty("ord_type")
    private String orderType;
    private Double price;
    @JsonProperty("state")
    private UpbitOrderState state;
    private String market;
    @JsonProperty("created_at")
    private String createdAt;
    private Double volume;
    @JsonProperty("remaining_volume")
    private Double remainingVolume;
    @JsonProperty("reserved_fee")
    private Double reversedFee;
    @JsonProperty("remaining_fee")
    private Double remainingFee;
    @JsonProperty("paid_fee")
    private Double paidFee;
    private Double locked;
    @JsonProperty("executed_volume")
    private Double executedVolume;
    @JsonProperty("trades_count")
    private Double tradesCount;

    public UpbitOrderResponse(String uuid, TradeType side, String orderType, Double price, UpbitOrderState state,
                              String market, String createdAt, Double volume) {
        this.uuid = uuid;
        this.side = side;
        this.orderType = orderType;
        this.price = price;
        this.state = state;
        this.market = market;
        this.createdAt = createdAt;
        this.volume = volume;
    }

    @JsonIgnore
    @Override
    public Boolean isBuyOrder() {
        return TradeType.BUY.equals(side);
    }

    @JsonIgnore
    @Override
    public String getCurrency() {
        return market.replace("KRW-", "");
    }

    @Override
    public void log() {
        System.out.format("|\t%16s\t|\t%4s\t|\t%10s\t|\t%,010.04f\t|\t%,010.010f\t|", createdAt.split("\\.")[0], side.toString(), market, price, volume);
        System.out.println();
    }

    @Override
    public SimulatedOrder toSimulatedOrder() {
        return SimulatedOrder.builder()
                .market(market)
                .price(price)
                .side(side)
                .volume(volume)
                .orderTime(LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }


}
