package com.trading.chart.item;

import com.trading.chart.common.ConvertType;
import com.trading.chart.common.CustomArrayList;
import com.trading.chart.item.response.ItemResponse;
import com.trading.chart.item.response.UpbitItem;
import com.trading.chart.tunnel.CallAPI;
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
