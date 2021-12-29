package com.github.hallwong.finance.repo.column;

import com.github.hallwong.finance.domain.Transaction;
import com.github.hallwong.finance.domain.TransactionDetail;
import com.github.hallwong.finance.domain.TransactionDetail_;
import com.github.hallwong.finance.domain.Transaction_;
import com.github.hallwong.finance.enumeration.TransactionStatus;
import com.github.hallwong.finance.enumeration.TransactionType;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class TransactionColumnHelper {

  public static Path<TransactionType> getType(Root<Transaction> root) {
    return root.get(Transaction_.detail).get(TransactionDetail_.type);
  }

  public static Path<TransactionStatus> getStatus(Root<Transaction> root) {
    return root.get(Transaction_.detail).get(TransactionDetail_.status);
  }

  public static Path<TransactionDetail> getDetail(Root<Transaction> root) {
    return root.get(Transaction_.detail);
  }

}
