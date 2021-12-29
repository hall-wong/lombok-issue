package com.github.hallwong.finance.domain;

import com.github.hallwong.finance.enumeration.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Entity
@DynamicUpdate
@NoArgsConstructor
public class Transaction extends BaseAuditableEntity {
    @Getter
    @Embedded
    private TransactionDetail detail;
    @Getter
    @Setter
    @ManyToOne
    private CounterParty counterParty;
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    private Transaction reference;
    @Getter
    @OneToMany(mappedBy = "reference", orphanRemoval = true)
    private List<Transaction> references = emptyList();

    public static Transaction of(CounterParty counterParty, TransactionDetail detail) {
        Transaction transaction = new Transaction();
        transaction.detail = detail;
        transaction.counterParty = counterParty;
        return transaction;
    }

    public void refunded(Transaction refund) {
        if (this.references.isEmpty()) {
            this.references = new ArrayList<>();
        }
        refund.reference = this;
        this.references.add(refund);
        int refundSum = references.stream().map(Transaction::getDetail).mapToInt(TransactionDetail::getAmount).sum();
        this.detail = this.detail.toBuilder()
          .serviceCharge(this.detail.getAmount() - refundSum)
          .status(TransactionStatus.REFUNDED)
          .build();
    }

}
