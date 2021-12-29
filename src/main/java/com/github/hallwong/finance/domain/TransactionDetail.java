package com.github.hallwong.finance.domain;

import com.github.hallwong.finance.enumeration.TransactionStatus;
import com.github.hallwong.finance.enumeration.TransactionType;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@Builder(access = AccessLevel.PROTECTED, toBuilder = true)
@Getter
@Setter
public class TransactionDetail {
    private String number;
    private String billNumber;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private String itemName;
    private Integer amount;
    private Integer serviceCharge;
    private String note;
    private LocalDateTime initAt;
    private LocalDateTime closedAt;
}
