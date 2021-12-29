package com.github.hallwong.finance.service.response;

import lombok.Data;

@Data
public class CounterPartySimpleResponse {
    private String name;
    private String tradingEntity = "me";
    private String note;
}
