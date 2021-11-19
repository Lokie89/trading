package com.trading.chart.application.item;

import com.trading.chart.common.ConvertType;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.item.response.UpbitItem;
import com.trading.chart.application.tunnel.CallAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@RequiredArgsConstructor
@Component
public class UpbitTradeItem implements TradeItem {

    private final String url = "https://api.upbit.com/v1/market/all?isDetails=true";
    private final CallAPI callAPI;

    @Override
    public List<ItemResponse> getItems() {
        String response = callAPI.get(url, HttpHeaders.EMPTY);
        UpbitItem[] candles = ConvertType.stringToType(response, UpbitItem[].class);
        return Arrays.asList(candles);
    }
}
