package com.trading.chart.application.message;

import com.trading.chart.application.simulator.request.SimulatorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/04/09
 */
@RequiredArgsConstructor
@Component
public class UpbitSimulatorMessenger implements SimulatorMessenger {
    private final MessageQueue messageQueue;

    @Override
    public void send(SimulatorRequest request) {
        messageQueue.publish(
                MessageKey.of(MessageClassification.THREAD),
                MessageRequest.builder().requestType(MessageType.SIMULATE).request(request).build()
        );
    }
}
