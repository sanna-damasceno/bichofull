package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetType;

import java.math.BigDecimal;

public class PrizeCalculator {

    public static BigDecimal calculatePrize(BetType type, BigDecimal amount) {

        switch (type) {

            case GROUP:
                return amount.multiply(BigDecimal.valueOf(18));

            case TEN:
                return amount.multiply(BigDecimal.valueOf(60));

            case THOUSAND:
                return amount.multiply(BigDecimal.valueOf(4000));

            default:
                return BigDecimal.ZERO;
        }
    }
}