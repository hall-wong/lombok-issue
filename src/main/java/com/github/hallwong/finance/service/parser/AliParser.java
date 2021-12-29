package com.github.hallwong.finance.service.parser;

import com.github.hallwong.finance.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toMap;

@Service
public class AliParser {

  public List<Transaction> parse(List<String> lines) {
    return Collections.emptyList();
  }

}
