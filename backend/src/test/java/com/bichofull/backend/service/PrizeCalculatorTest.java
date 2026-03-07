package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrizeCalculatorTest {

    //Group
    @Test
    void shouldCalculateGroupPrize() {

        BigDecimal amount = BigDecimal.valueOf(10);

        BigDecimal prize = PrizeCalculator.calculatePrize(
                BetType.GROUP,
                amount
        );

        assertEquals(BigDecimal.valueOf(180), prize);
    }

    // Ten
    @Test
    void shouldCalculateTenPrize() {

        BigDecimal amount = BigDecimal.valueOf(10);

        BigDecimal prize = PrizeCalculator.calculatePrize(
                BetType.TEN,
                amount
        );

        assertEquals(BigDecimal.valueOf(600), prize);
    }
    
    //Thousand
    @Test
    void shouldCalculateThousandPrize() {

        BigDecimal amount = BigDecimal.valueOf(10);

        BigDecimal prize = PrizeCalculator.calculatePrize(
                BetType.THOUSAND,
                amount
        );

        assertEquals(BigDecimal.valueOf(40000), prize);
    }
}
