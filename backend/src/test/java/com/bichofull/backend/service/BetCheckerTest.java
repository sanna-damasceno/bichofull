package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BetCheckerTest {

    private final List<String> drawResults = List.of("1234", "5678", "9012", "3456", "7890");

    @Test
    @DisplayName("Deve ganhar Grupo se estiver presente em qualquer um dos 5 prêmios")
    void shouldWinGroupBetAnyPosition() {
        Bet bet = new Bet();
        bet.setType(BetType.GROUP);
        // Grupo 09 (Cobra) corresponde às dezenas 33, 34, 35, 36.
        // "1234" termina em 34 (Grupo 9) no 1º prêmio.
        bet.setChosenNumber("9");

        assertTrue(BetChecker.isWinner(bet, drawResults));

        // Grupo 23 (Cobra) dezenas 89, 90, 91, 92. 
        // "7890" termina em 90 (Grupo 23) no 5º prêmio.
        bet.setChosenNumber("23");
        assertTrue(BetChecker.isWinner(bet, drawResults));
    }

    @Test
    @DisplayName("Deve ganhar Dezena se estiver presente em qualquer um dos 5 prêmios")
    void shouldWinTenBetAnyPosition() {
        Bet bet = new Bet();
        bet.setType(BetType.TEN);
        
        // Dezena 34 está no 1º prêmio (1234)
        bet.setChosenNumber("34");
        assertTrue(BetChecker.isWinner(bet, drawResults));

        // Dezena 90 está no 5º prêmio (7890)
        bet.setChosenNumber("90");
        assertTrue(BetChecker.isWinner(bet, drawResults));
    }

    @Test
    @DisplayName("Deve ganhar Milhar apenas se acertar o 1º prêmio")
    void shouldWinThousandBetOnlyFirstPrize() {
        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        
        // Acertando o primeiro
        bet.setChosenNumber("1234");
        assertTrue(BetChecker.isWinner(bet, drawResults));

        // Se o número escolhido estiver no 2º prêmio, deve perder
        bet.setChosenNumber("5678");
        assertFalse(BetChecker.isWinner(bet, drawResults));
    }

    @Test
    @DisplayName("Deve perder Grupo se não estiver em nenhum dos prêmios")
    void shouldLoseGroupBet() {
        Bet bet = new Bet();
        bet.setType(BetType.GROUP);
        bet.setChosenNumber("17"); // Grupo 17 (Macaco): 65, 66, 67, 68

        assertFalse(BetChecker.isWinner(bet, drawResults));
    }

    @Test
    @DisplayName("Deve perder Dezena se não estiver em nenhum dos prêmios")
    void shouldLoseTenBet() {
        Bet bet = new Bet();
        bet.setType(BetType.TEN);
        bet.setChosenNumber("19");

        assertFalse(BetChecker.isWinner(bet, drawResults));
    }

    @Test
    @DisplayName("Deve perder Milhar se o número não for o do 1º prêmio")
    void shouldLoseThousandBet() {
        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setChosenNumber("4321");

        assertFalse(BetChecker.isWinner(bet, drawResults));
    }
}