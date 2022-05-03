package com.trading.chart.application.message;

import com.trading.chart.application.chart.request.ChartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/05/03
 */
@RequiredArgsConstructor
@Component
public class UpbitChartMessenger implements Messenger {

    private final MessageQueue messageQueue;

    @Override
    public void send(Object request) {
        ChartRequest chartRequest = (ChartRequest) request;
        chartRequest.toMessageRequest()
                .forEach(req ->
                        messageQueue.publish(
                                MessageKey.of(MessageClassification.UPBIT_QUOTATION_API),
                                MessageRequest.builder().requestType(MessageType.CALL_API_CHART).request(req).build()
                        )
                );
    }
}
