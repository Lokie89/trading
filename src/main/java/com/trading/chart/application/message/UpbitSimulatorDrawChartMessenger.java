package com.trading.chart.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/05/03
 */
@RequiredArgsConstructor
@Component
public class UpbitSimulatorDrawChartMessenger implements Messenger {

    private final MessageQueue messageQueue;

    @Override
    public void send(Object request) {
        messageQueue.publish(
                MessageKey.of(MessageClassification.THREAD),
                MessageRequest.builder().requestType(MessageType.SIMULATE_DRAW_CHART).request(request).build()
        );
    }
}
