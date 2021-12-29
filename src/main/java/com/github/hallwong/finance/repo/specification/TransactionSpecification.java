package com.github.hallwong.finance.repo.specification;

import com.github.hallwong.finance.domain.CounterParty_;
import com.github.hallwong.finance.domain.Transaction;
import com.github.hallwong.finance.domain.TransactionDetail_;
import com.github.hallwong.finance.domain.Transaction_;
import com.github.hallwong.finance.enumeration.TransactionStatus;
import com.github.hallwong.finance.enumeration.TransactionType;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static com.github.hallwong.finance.repo.column.TransactionColumnHelper.*;
import static org.springframework.data.jpa.domain.Specification.not;

@UtilityClass
public final class TransactionSpecification {

  public Specification<Transaction> list(LocalDate since, String input) {
    return isNotClose().and(isNotRefund()).and(since(since)).and(byCounterParty(input)).and(orderByInit());
  }

  public Specification<Transaction> isNotTotallyRefunded() {
    return (root, query, cb) ->
      cb.and(
        cb.equal(getType(root), TransactionType.OUTGOING),
        cb.equal(getStatus(root), TransactionStatus.REFUNDED),
        cb.notEqual(getDetail(root).get(TransactionDetail_.serviceCharge), 0)
      );
  }

  public Specification<Transaction> isOutingGoing() {
    return (root, query, cb) ->
      cb.and(
        cb.equal(getType(root), TransactionType.OUTGOING),
        getStatus(root).in(TransactionStatus.SUCCESS, TransactionStatus.ONGOING)
      );
  }

  public Specification<Transaction> since(LocalDate since) {
    return (root, query, cb) ->
      cb.greaterThanOrEqualTo(getDetail(root).get(TransactionDetail_.initAt), since.atStartOfDay());
  }

  private static Specification<Transaction> byCounterParty(String name) {
    return (root, query, cb) -> name == null ? null : cb.equal(root.get(Transaction_.counterParty).get(CounterParty_.name), name);
  }

  private static Specification<Transaction> orderByInit() {
    return (root, query, cb) -> {
      query.orderBy(cb.asc(getDetail(root).get(TransactionDetail_.initAt)));
      return null;
    };
  }

  private static Specification<Transaction> isClose() {
    return (root, query, cb) ->
      cb.equal(getStatus(root), TransactionStatus.CLOSE);
  }

  private static Specification<Transaction> isNotClose() {
    return not(isClose());
  }

  private static Specification<Transaction> isRefund() {
    return (root, query, cb) ->
      cb.equal(getType(root), TransactionType.REFUND);
  }

  private static Specification<Transaction> isNotRefund() {
    return not(isRefund());
  }


}
