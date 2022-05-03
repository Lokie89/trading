package com.trading.chart.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2022/05/03
 */
@RequiredArgsConstructor
@Component
public class UpbitMarketMessenger implements Messenger {

    private final MessageQueue messageQueue;

    @Override
    public void send(Object request) {
        messageQueue.publish(MessageKey.of(MessageClassification.UPBIT_QUOTATION_API), MessageRequest.builder().requestType(MessageType.CALL_API_MARKET).build());
    }
}
