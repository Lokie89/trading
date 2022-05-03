package com.trading.chart.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */

@RequiredArgsConstructor
@Component
public class MessageQueue {

    private static final Map<MessageKey, ConcurrentLinkedQueue<MessageRequest>> MESSAGE_QUEUE = new ConcurrentHashMap<>();

    static {
        Arrays.stream(MessageClassification.values()).forEach(classification ->
                MESSAGE_QUEUE.put(MessageKey.of(classification), new ConcurrentLinkedQueue<>())
        );
    }

    public void publish(MessageKey key, MessageRequest value) {
        ConcurrentLinkedQueue<MessageRequest> pub = MESSAGE_QUEUE.get(key);
        pub.add(value);
    }

    public void publish(MessageKey key, List<MessageRequest> values) {
        ConcurrentLinkedQueue<MessageRequest> pub = MESSAGE_QUEUE.get(key);
        pub.addAll(values);
    }

    public ConcurrentLinkedQueue<MessageRequest> subscribe(MessageKey key) {
        return MESSAGE_QUEUE.get(key);
    }
}
