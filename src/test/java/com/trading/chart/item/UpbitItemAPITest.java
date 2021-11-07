package com.trading.chart.item;

import com.trading.chart.common.CustomArrayList;
import com.trading.chart.item.response.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SeongRok.Oh
 * @since 2021/11/07
 */
@DisplayName("업비트 Market 호출 테스트")
@SpringBootTest
public class UpbitItemAPITest {

    @Autowired
    TradeItemAPI upbitTradeItemAPI;

    @DisplayName("업비트 마켓내역 가져오기")
    @Test
    void getItemsTest() {
        final String krwBtcMarket = "KRW-BTC";
        CustomArrayList<ItemResponse> items = upbitTradeItemAPI.getItems();
        assertTrue(
                items.stream().anyMatch(item->item.getName().equals(krwBtcMarket))
        );
    }


}
