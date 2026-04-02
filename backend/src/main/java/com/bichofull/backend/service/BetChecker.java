package com.bichofull.backend.service;

import java.util.List;

import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;

public class BetChecker {

    public static boolean isWinner(Bet bet, List<String> results) {

        String chosen = bet.getChosenNumber();
        String firstPrize = results.get(0);

        switch (bet.getType()) {

            case THOUSAND:
                return firstPrize.equals(chosen);

            case TEN:
                return results.stream()
                        .anyMatch(res -> res.substring(2).equals(chosen));

            case GROUP:
                return results.stream()
                        .anyMatch(res -> getGroup(res) == Integer.parseInt(chosen));

            default:
                return false;
        }
    }

    private static int getGroup(String number) {

        int lastTwo = Integer.parseInt(number.substring(2));

        if (lastTwo == 0) return 25; 
        return (lastTwo - 1) / 4 + 1;
    }
}