package com.trading.chart.domain.user;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@Getter
@Entity
public class User {
    @Id
    private String id;
    private String password;
    private String upbitAccessKey;
    private String upbitSecretKey;
}
