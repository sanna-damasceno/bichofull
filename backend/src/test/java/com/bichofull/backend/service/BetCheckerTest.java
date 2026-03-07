package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BetCheckerTest {
    // GROUP ganha
    @Test
    void shouldWinGroupBet() {

        Bet bet = new Bet();
        bet.setType(BetType.GROUP);
        bet.setChosenNumber("9");

        boolean result = BetChecker.isWinner(bet, "1234");

        assertTrue(result);
    }

    // TEN ganha

    @Test
    void shouldWinTenBet() {

        Bet bet = new Bet();
        bet.setType(BetType.TEN);
        bet.setChosenNumber("34");

        boolean result = BetChecker.isWinner(bet, "1234");

        assertTrue(result);
    }
    
    // THOUSAND ganha
    @Test
    void shouldWinThousandBet() {

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setChosenNumber("1234");

        boolean result = BetChecker.isWinner(bet, "1234");

        assertTrue(result);
    }

    // Group perde
    @Test
    void shouldLoseGroupBet() {

        Bet bet = new Bet();
        bet.setType(BetType.GROUP);
        bet.setChosenNumber("17");

        boolean result = BetChecker.isWinner(bet, "1234");

        assertFalse(result);
    }

    // Ten perde
    @Test
    void shouldLoseTenBet() {

        Bet bet = new Bet();
        bet.setType(BetType.TEN);
        bet.setChosenNumber("19");

        boolean result = BetChecker.isWinner(bet, "1234");

        assertFalse(result);
    }

    // Ten perde
    @Test
    void shouldLoseThousandBet() {

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setChosenNumber("4321");

        boolean result = BetChecker.isWinner(bet, "1234");

        assertFalse(result);
    }
    

}
