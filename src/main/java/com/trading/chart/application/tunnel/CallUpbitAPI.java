package com.trading.chart.application.tunnel;

import com.trading.chart.application.candle.response.UpbitCandleResponse;
import com.trading.chart.application.item.UpbitTradeItem;
import com.trading.chart.application.item.response.UpbitItem;
import com.trading.chart.common.ConvertType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2022/10/19
 */
@RequiredArgsConstructor
@Component
public class CallUpbitAPI {

    private static final int EXCHANGE_REQUEST_LIMIT = 8;
    private static final int QUOTATION_REQUEST_LIMIT = 10;

    private final QuotationTopics quotationTopics;
    private final UpbitCandleCache candleCache;
    private final UpbitItemCache itemCache;
    private final CallAPI callAPI;


    @Scheduled(cron = "0/1 * 0-2,4-23 * * *")
    public void subscribeQuotation() {
        for (int i = 0; i < QUOTATION_REQUEST_LIMIT; i++) {
            QuotationTopic topic = quotationTopics.pop();
            String url = topic.getUrl();
            String response = callAPI.get(url, HttpHeaders.EMPTY);
            // 마켓
            if (QuotationType.ITEM.equals(topic.getType())) {
                UpbitItem[] items = ConvertType.stringToType(response, UpbitItem[].class);
                itemCache.update(items);
            }
            // 캔들
            if (QuotationType.CANDLE.equals(topic.getType())) {
                UpbitCandleResponse[] candles = ConvertType.stringToType(response, UpbitCandleResponse[].class);
                candleCache.update(candles);
            }
        }
    }
}
