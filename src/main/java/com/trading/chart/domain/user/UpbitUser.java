package com.trading.chart.domain.user;

import com.trading.chart.application.trader.response.AccountResponses;
import com.trading.chart.domain.user.response.UpbitUserResponse;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@Entity
public class UpbitUser {
    @Id
    private String id;
    @Column(nullable = false)
    private String password;
    @Getter
    private String upbitAccessKey;
    @Getter
    private String upbitSecretKey;

    @ColumnDefault(value = "true")
    @Column(name = "buying")
    private Boolean isBuying;
    @ColumnDefault(value = "0x7fffffff")
    private Integer buyLimit;

    @ColumnDefault(value = "5000")
    private Integer cashAtOnce;
    @ColumnDefault(value = "true")
    @Column(name = "selling")
    private Boolean isSelling;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UpbitTradeResource> tradeResources;

    public UpbitUserResponse toDto(AccountResponses upbitAccounts) {
        return UpbitUserResponse.builder()
                .id(id)
                .buying(isBuying)
                .selling(isSelling)
                .buyLimit(buyLimit)
                .cashAtOnce(cashAtOnce)
                .accounts(upbitAccounts)
                .tradeResources(
                        tradeResources.stream()
                                .map(UpbitTradeResource::toDto)
                                .collect(Collectors.toList()))
                .build();
    }
}
