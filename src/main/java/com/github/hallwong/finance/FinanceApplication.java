package com.github.hallwong.finance;

import com.fasterxml.jackson.databind.Module;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.util.TimeZone;

@SpringBootApplication
@RestController
@EnableJpaAuditing
public class FinanceApplication {

    @SneakyThrows
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(FinanceApplication.class);
    }

    @Bean
    public Module moneyModule() {
        return new MoneyModule();
    }

}
