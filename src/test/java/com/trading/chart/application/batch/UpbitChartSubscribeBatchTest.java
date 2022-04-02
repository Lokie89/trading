package com.trading.chart.application.batch;

import com.trading.chart.application.message.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author SeongRok.Oh
 * @since 2022/03/31
 */
@DisplayName("업비트 메세지 큐 테스트")
@SpringBootTest
public class UpbitChartSubscribeBatchTest {
    @Autowired
    MessageQueue messageQueue;

    @Autowired
    UpbitChartBatch batch;

    @Autowired
    UpbitChartSubscribeBatch subscribeBatch;

    @Test
    void publishTest() {
        messageQueue.publish(MessageKey.of(MessageClassification.UPBIT_QUOTATION_API), List.of(MessageRequest.builder().requestType(MessageType.CALL_API_MARKET).build()));
        ConcurrentLinkedQueue<MessageRequest> messageRequests = messageQueue.subscribe(MessageKey.of(MessageClassification.UPBIT_QUOTATION_API));
        while (!messageRequests.isEmpty()) {
            MessageRequest request = messageRequests.poll();
            Object object = request.getRequest();
        }
    }

    @Test
    void batchTest(){
        batch.operateChartOneMinute();
        subscribeBatch.callUpbitApiRequest();
        subscribeBatch.runWithThread();
    }
}
