package com.github.hallwong.finance.repo;

import com.github.hallwong.finance.domain.CounterParty_;
import com.github.hallwong.finance.domain.Transaction;
import com.github.hallwong.finance.domain.TransactionDetail_;
import com.github.hallwong.finance.domain.Transaction_;
import com.github.hallwong.finance.enumeration.TransactionStatus;
import com.github.hallwong.finance.service.response.TransactionSumByCounterPartyResponse;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.hallwong.finance.repo.column.TransactionColumnHelper.getDetail;
import static com.github.hallwong.finance.repo.column.TransactionColumnHelper.getStatus;
import static com.github.hallwong.finance.repo.specification.TransactionSpecification.*;

@Repository
@RequiredArgsConstructor
public class TransactionStatisticsRepository {

  private final EntityManager em;

  public List<TransactionSumByCounterPartyResponse> sumByCounterParty(LocalDate since) {
    var cb = em.getCriteriaBuilder();
    var tupleQuery = cb.createTupleQuery();
    var root = tupleQuery.from(Transaction.class);
    var counterPartyJoin = root.join(Transaction_.counterParty, JoinType.LEFT);
    var amountSum = cb.sum(cb.<Integer>selectCase()
      .when(cb.equal(getStatus(root), TransactionStatus.REFUNDED), getDetail(root).get(TransactionDetail_.serviceCharge))
      .otherwise(getDetail(root).get(TransactionDetail_.amount)));
    tupleQuery.multiselect(counterPartyJoin.get(CounterParty_.name), amountSum.alias("amount_sum"));
    tupleQuery.where(isNotTotallyRefunded().or(isOutingGoing()).and(since(since)).toPredicate(root, tupleQuery, cb));
    tupleQuery.groupBy(counterPartyJoin.get(CounterParty_.name));
    tupleQuery.orderBy(cb.desc(amountSum));
    return em.createQuery(tupleQuery).setMaxResults(30).getResultList().stream()
      .map(tuple -> {
        var resp = new TransactionSumByCounterPartyResponse();
        resp.setCounterPartyName(tuple.get(0, String.class));
        Money amount = Money.of(tuple.get(1, Long.class), "CNY").divide(100);
        resp.setAmount(amount);
        return resp;
      }).collect(Collectors.toList());
  }


}
