package com.trading.chart.application.item;

import com.trading.chart.application.item.response.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@DisplayName("업비트 Market 호출 테스트")
@SpringBootTest
public class UpbitItemTest {

    @Autowired
    TradeItem upbitTradeItem;

    @DisplayName("업비트 마켓내역 가져오기")
    @Test
    void getItemsTest() {
        final String krwBtcMarket = "KRW-BTC";
        SortedSet<ItemResponse> items = upbitTradeItem.getItems();
        assertTrue(
                items.stream().anyMatch(item -> item.getName().equals(krwBtcMarket))
        );
    }


}
