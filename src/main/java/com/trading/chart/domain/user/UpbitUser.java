package com.trading.chart.domain.user;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@Getter
@Entity
public class UpbitUser {
    @Id
    private String id;
    private String password;
    private String upbitAccessKey;
    private String upbitSecretKey;

    @ColumnDefault(value = "false")
    private Boolean isBuying;
    // 계정마다 매수 한도 ( 0 -> 최대 )
    @ColumnDefault(value = "0")
    private Double buyLimit;

    private Integer cashAtOnce;
    @ColumnDefault(value = "false")
    private Boolean isSelling;

}
