package com.trading.chart.item.response;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * market : 업비트에서 제공중인 시장 정보
 * korean_name : 거래 대상 암호화폐 한글명
 * english_name : 거래 대상 암호화폐 영문명
 * market_warning : 유의 종목 여부 / NONE (해당 사항 없음), CAUTION(투자유의)
 */
public class UpbitItem implements ItemResponse {

    @ApiModelProperty(value = "종목이름", example = "KRW-BTC")
    @JsonProperty("market")
    @Getter
    private String name;
    @ApiModelProperty(value = "한글명", example = "비트코인")
    @JsonProperty("korean_name")
    private String koreanName;
    @ApiModelProperty(value = "영문명", example = "Bitcoin")
    @JsonProperty("english_name")
    private String englishName;
    @ApiModelProperty(value = "유의여부", example = "CAUTION")
    @JsonProperty("market_warning")
    @Getter
    private ItemStatus status;

}
