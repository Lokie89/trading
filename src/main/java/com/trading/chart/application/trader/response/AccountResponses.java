package com.trading.chart.application.trader.response;

import com.trading.chart.application.chart.response.ChartResponse;
import com.trading.chart.application.chart.response.ChartResponses;
import com.trading.chart.application.order.response.OrderResponse;
import com.trading.chart.application.trade.request.TradeRequest;
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

    // TODO : Map 으로 변경?
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

    public void apply(OrderResponse response) {
        if (response.isBuyOrder()) {
            add(response);
        }
        if (!response.isBuyOrder()) {
            remove(response);
        }
    }

    private void remove(OrderResponse response) {
        final String currency = response.getCurrency();
        Optional<AccountResponse> optExisting = getAccount(currency);
        if (optExisting.isPresent()) {
            AccountResponse existing = optExisting.get();
            existing.sellBalance(response.getVolume());
            if (!existing.isOwn()) {
                accounts.remove(existing);
            }
            Double total = response.getPrice() * response.getVolume();
            earnCash(total);
        }
    }

    private void add(OrderResponse response) {
        final String currency = response.getCurrency();
        final Double volume = Objects.isNull(response.getVolume()) ? 1 : response.getVolume();
        final Double price = Objects.isNull(response.getPrice()) ? 1 : response.getPrice();
        AccountResponse accountResponse = UpbitAccount.of(currency, volume, price);
        Optional<AccountResponse> optExisting = getAccount(currency);
        Double total = price * volume;
        useCash(total);
        if (optExisting.isPresent()) {
            AccountResponse existing = optExisting.get();
            Double existingTotal = existing.getBalance() * existing.getAvgBuyPrice();
            double totalVolume = existing.getBalance() + volume;
            accounts.remove(existing);
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

    public Optional<AccountResponse> getAccount(final String currency) {
        return accounts.stream()
                .filter(accountResponse -> currency.replace("KRW-", "").equals(accountResponse.getCurrency()))
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

    public double usedCash() {
        return accounts.stream()
                .filter(account -> !account.getCurrency().equals("KRW"))
                .mapToDouble(account -> account.getBalance() * account.getAvgBuyPrice())
                .sum()
                ;
    }

    public boolean isAffordable(TradeRequest tradeRequest) {
        return tradeRequest.isLessPrice(getCash());
    }

    public void logKrw(List<ChartResponse> chartResponses) {
        System.out.format("|\t%5s\t|\t%,d\t|", "KRW", accounts.stream().mapToInt(account->account.toKrw(chartResponses)).sum());
        System.out.println();
    }

    public void logAll() {
        accounts.forEach(AccountResponse::log);
    }
}
