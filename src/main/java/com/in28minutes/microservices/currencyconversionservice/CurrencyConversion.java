package com.in28minutes.microservices.currencyconversionservice;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CurrencyConversion {
    private Long id;
    private String from;
    private String to;
    private BigDecimal quantity;
    private BigDecimal conversionMultiple;
    private BigDecimal totalCalculatedAmount;
    private String environment;

    public CurrencyConversion(Long id,
                              String from,
                              String to,
                              BigDecimal quantity,
                              BigDecimal conversionMultiple,
                              BigDecimal totalCalculatedAmount,
                              String environment) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.quantity = quantity;
        this.conversionMultiple = conversionMultiple;
        this.totalCalculatedAmount = totalCalculatedAmount;
        this.environment = environment;
    }
}
