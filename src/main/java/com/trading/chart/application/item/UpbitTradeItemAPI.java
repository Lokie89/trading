package com.trading.chart.application.item;

import com.trading.chart.application.common.ConvertType;
import com.trading.chart.application.common.CustomArrayList;
import com.trading.chart.application.item.response.ItemResponse;
import com.trading.chart.application.item.response.UpbitItem;
import com.trading.chart.application.tunnel.CallAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@RequiredArgsConstructor
@Component
public class UpbitTradeItemAPI implements TradeItemAPI {

    private final String url = "https://api.upbit.com/v1/market/all?isDetails=true";
    private final CallAPI callAPI;

    @Override
    public CustomArrayList<ItemResponse> getItems() {
        String response = callAPI.get(url, HttpHeaders.EMPTY);
        UpbitItem[] candles = ConvertType.stringToType(response, UpbitItem[].class);
        return new CustomArrayList<>(Arrays.asList(candles));
    }
}
