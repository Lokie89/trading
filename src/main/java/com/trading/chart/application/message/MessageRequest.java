package com.trading.chart.application.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MessageRequest {
    private MessageType requestType;
    private Object request;
}
