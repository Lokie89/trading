package com.trading.chart.application.scheduler;

import com.trading.chart.application.message.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author SeongRok.Oh
 * @since 2022/03/31
 */

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("업비트 메세지 큐 테스트")
@SpringBootTest
public class UpbitChartSubscribeSchedulerTest {
    @Autowired
    MessageQueue messageQueue;

    @Autowired
    UpbitChartPublishScheduler publishScheduler;

    @Autowired
    UpbitChartSubscribeScheduler subscribeScheduler;

    @Order(1)
    @Test
    void publishTest() {
        messageQueue.publish(MessageKey.of(MessageClassification.UPBIT_QUOTATION_API), List.of(MessageRequest.builder().requestType(MessageType.CALL_API_MARKET).build()));
        ConcurrentLinkedQueue<MessageRequest> messageRequests = messageQueue.subscribe(MessageKey.of(MessageClassification.UPBIT_QUOTATION_API));
        while (!messageRequests.isEmpty()) {
            MessageRequest request = messageRequests.poll();
            Object object = request.getRequest();
        }
    }

    @Order(2)
    @Test
    void batchTest() {
        publishScheduler.operateChartOneMinute();
        subscribeScheduler.callUpbitApiRequest();
        subscribeScheduler.runWithThread();
        publishScheduler.archiveChart();
    }
}
