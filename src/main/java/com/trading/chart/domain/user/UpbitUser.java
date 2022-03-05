package com.trading.chart.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UpbitUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Getter
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Getter
    @Column(unique = true)
    private String upbitClient;
    @Getter
    private String upbitAccessKey;
    @Getter
    private String upbitSecretKey;

    @ColumnDefault(value = "true")
    @Column(name = "buying", nullable = false)
    private Boolean isBuying;
    @Column(nullable = false)
    @ColumnDefault(value = "0x7fffffff")
    private Integer buyLimit;

    @Column(nullable = false)
    @ColumnDefault(value = "5000")
    private Integer cashAtOnce;
    @ColumnDefault(value = "true")
    @Column(name = "selling", nullable = false)
    private Boolean isSelling;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trade_resource",
            joinColumns = @JoinColumn(name = "upbit_user_id"))
    private List<TradeResource> tradeResources;

}
