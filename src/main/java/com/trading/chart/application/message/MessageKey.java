package com.trading.chart.application.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageKey {
    private MessageClassification classification;

    public static MessageKey of(MessageClassification classification) {
        return new MessageKey(classification);
    }

}
