package com.github.hallwong.finance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@ToString
public class CounterParty extends BaseEntity {
    @Getter
    @ToString.Include
    private String name;
    @Getter
    private String note;

    public static CounterParty autoCreate(String name) {
        CounterParty counterParty = new CounterParty();
        counterParty.name = name;
        counterParty.note = "自动创建：" + name;
        return counterParty;
    }

}
