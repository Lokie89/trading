package com.trading.chart.application.trader.response;

import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author SeongRok.Oh
 * @since 2021/12/10
 */
@RequiredArgsConstructor
public class AccountResponses {

    private final List<AccountResponse> accounts;

    public static AccountResponses of(UpbitAccount... account) {
        return new AccountResponses(new ArrayList<>(List.of(account)));
    }

    public static AccountResponses of(List<AccountResponse> accountResponseList) {
        return new AccountResponses(accountResponseList);
    }

    public Stream<AccountResponse> stream() {
        return accounts.stream();
    }

    public void apply(UpbitOrderRequest request) {
        if (TradeType.BUY.equals(TradeType.fromString(request.getSide()))) {
            add(request);
        }
        if (TradeType.SELL.equals(TradeType.fromString(request.getSide()))) {
            remove(request);
        }
    }

    private void remove(UpbitOrderRequest request) {
        final String currency = request.getMarket().replace("KRW-", "");
        Optional<AccountResponse> optExisting = getAccount(currency);
        if (optExisting.isPresent()) {
            AccountResponse existing = optExisting.get();
            existing.sellBalance(request.getVolume());
            Double total = request.getPrice() * request.getVolume();
            if (!existing.isOwn()) {
                accounts.remove(existing);
            }
            earnCash(total);
        }
    }

    private void add(UpbitOrderRequest request) {
        final String currency = request.getMarket().replace("KRW-", "");
        AccountResponse accountResponse = UpbitAccount.of(currency, request.getVolume(), request.getPrice());
        Optional<AccountResponse> optExisting = getAccount(currency);
        useCash(request.getVolume() * request.getPrice());
        if (optExisting.isPresent()) {
            AccountResponse existing = optExisting.get();
            Double existingTotal = existing.getBalance() * existing.getAvgBuyPrice();
            Double total = request.getPrice() * request.getVolume();
            double totalVolume = existing.getBalance() + request.getVolume();
            accounts.add(
                    UpbitAccount.of(currency, totalVolume,
                            (existingTotal + total) / totalVolume)
            );
        } else {
            accounts.add(accountResponse);
        }
    }

    private AccountResponse getCash() {
        return getAccount("KRW").orElseThrow(RuntimeException::new);
    }

    private Optional<AccountResponse> getAccount(final String currency) {
        return accounts.stream()
                .filter(accountResponse -> currency.equals(accountResponse.getCurrency()))
                .findAny()
                ;
    }

    private void useCash(Double balance) {
        getCash().sellBalance(balance);
    }

    private void earnCash(Double balance) {
        getCash().buyBalance(balance);
    }

    public int size() {
        return accounts.size();
    }
}
