package com.trading.chart.domain.user;

import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.UpbitUserResponse;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
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
    @Column(name = "buying")
    private Boolean isBuying;
    // 계정마다 매수 한도 ( 0 -> 최대 )
    @ColumnDefault(value = "0")
    private Integer buyLimit;

    @ColumnDefault(value = "5000")
    private Integer cashAtOnce;
    @ColumnDefault(value = "false")
    @Column(name = "selling")
    private Boolean isSelling;

    public UpbitUserResponse toDto(AccountResponses upbitAccounts) {
        return UpbitUserResponse.builder()
                .id(id)
                .buying(isBuying)
                .selling(isSelling)
                .buyLimit(buyLimit)
                .cashAtOnce(cashAtOnce)
                .accounts(upbitAccounts)
                .build();
    }
}
