package com.trading.chart.application.message;

import com.trading.chart.application.chart.request.UpbitSimulatorChartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/05/03
 */
@RequiredArgsConstructor
@Component
public class UpbitSimulatorChartMessenger implements Messenger {

    private final MessageQueue messageQueue;

    @Override
    public void send(Object request) {
        UpbitSimulatorChartRequest chartRequest = (UpbitSimulatorChartRequest) request;
        chartRequest = chartRequest.toSimulateRequest();
        chartRequest.toMessageRequest()
                .forEach(req ->
                        messageQueue.publish(
                                MessageKey.of(MessageClassification.UPBIT_QUOTATION_API),
                                MessageRequest.builder().requestType(MessageType.SIMULATE_CALL_API_CHART).request(req).build()
                        )
                );
    }
}
