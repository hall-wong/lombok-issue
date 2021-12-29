package com.github.hallwong.finance.service.response;

import com.github.hallwong.finance.enumeration.TransactionStatus;
import com.github.hallwong.finance.enumeration.TransactionType;
import lombok.Data;
import org.javamoney.moneta.Money;

import java.time.Instant;

@Data
public class TransactionSimpleResponse {
    private TransactionStatus status;
    private TransactionType type;
    private String number;
    private String billNumber;
    private String itemName;
    private String note;
    private CounterPartySimpleResponse counterParty;
    private Money amount;
    private Money serviceCharge;
    private TransactionSimpleResponse reference;
    private Instant initAt;
}
