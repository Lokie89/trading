package com.trading.chart.domain.user;

import lombok.*;

import javax.persistence.*;

/**
 * @author SeongRok.Oh
 * @since 2022/03/05
 */

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Getter
    @Column(unique = true)
    private String client;
    private String password;

}
