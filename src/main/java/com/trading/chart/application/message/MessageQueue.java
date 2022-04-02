package com.trading.chart.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author SeongRok.Oh
 * @since 2022/03/27
 */

@RequiredArgsConstructor
@Component
public class MessageQueue {

    private static final Map<MessageKey, ConcurrentLinkedQueue<MessageRequest>> publish = new ConcurrentHashMap<>();

    public void publish(MessageKey key, MessageRequest value) {

        ConcurrentLinkedQueue<MessageRequest> pub = publish.get(key);
        if(Objects.isNull(pub) || pub.isEmpty()){
            pub = new ConcurrentLinkedQueue<>();
            publish.put(key, pub);
        }
        pub.add(value);
    }

    public void publish(MessageKey key, List<MessageRequest> values) {
        ConcurrentLinkedQueue<MessageRequest> pub = publish.get(key);
        if(Objects.isNull(pub) || pub.isEmpty()){
            pub = new ConcurrentLinkedQueue<>();
            publish.put(key, pub);
        }
        pub.addAll(values);
    }

    public ConcurrentLinkedQueue<MessageRequest> subscribe(MessageKey key) {
        return publish.get(key);
    }
}
