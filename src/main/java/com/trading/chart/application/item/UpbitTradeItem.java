package com.trading.chart.application.item;

import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.common.ConvertType;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.item.response.UpbitItem;
import com.trading.chart.application.tunnel.CallAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@RequiredArgsConstructor
@Component
public class UpbitTradeItem implements TradeItem {

    private final String url = "https://api.upbit.com/v1/market/all?isDetails=true";
    private final CallAPI callAPI;
    private SortedSet<ItemResponse> cache;

    @Override
    public SortedSet<ItemResponse> getItems() {
        if (Objects.nonNull(cache) && cache.size() > 0) {
            return cache;
        }
        String response = callAPI.get(url, HttpHeaders.EMPTY);
        UpbitItem[] candles = ConvertType.stringToType(response, UpbitItem[].class);
        cache = new TreeSet<>(Comparator.comparing(ItemResponse::getName));
        cache.addAll(Arrays.asList(candles));
        return cache;
    }

    @Override
    public SortedSet<ItemResponse> getKrwItems() {
        return getItems().stream()
                .filter(ItemResponse::isKrwMarket)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ItemResponse::getName))))
                ;
    }

    @Override
    public void update() {
        cache = new TreeSet<>(Comparator.comparing(ItemResponse::getName));
        getItems();
    }
}
