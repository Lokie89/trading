package com.trading.chart.application.trader.response;

import com.trading.chart.application.order.request.TradeType;
import com.trading.chart.application.order.request.UpbitOrderRequest;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        final Double volume = Objects.isNull(request.getVolume()) ? 1 : request.getVolume();
        final Double price = Objects.isNull(request.getPrice()) ? 1 : request.getPrice();
        AccountResponse accountResponse = UpbitAccount.of(currency, volume, price);
        Optional<AccountResponse> optExisting = getAccount(currency);
        useCash(volume * price);
        if (optExisting.isPresent()) {
            AccountResponse existing = optExisting.get();
            Double existingTotal = existing.getBalance() * existing.getAvgBuyPrice();
            Double total = price * volume;
            double totalVolume = existing.getBalance() + volume;
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

    public boolean remainCash(int compare) {
        return getCash().getBalance() >= compare;
    }

    public double usedCash() {
        return accounts.stream()
                .filter(account -> !account.getCurrency().equals("KRW"))
                .mapToDouble(account -> account.getBalance() * account.getAvgBuyPrice())
                .sum()
                ;
    }
}
