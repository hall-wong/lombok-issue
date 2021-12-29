package com.github.hallwong.finance.service.response;

import lombok.Data;
import org.javamoney.moneta.Money;

@Data
public class TransactionSumByCounterPartyResponse {
    private String counterPartyName;
    private Money amount;
}
