package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;

public class BetChecker {

    public static boolean isWinner(Bet bet, String result) {

        String chosen = bet.getChosenNumber();

        switch (bet.getType()) {

            case THOUSAND:
                return result.equals(chosen);

            case TEN:
                return result.substring(2).equals(chosen);

            case GROUP:
                return getGroup(result) == Integer.parseInt(chosen);

            default:
                return false;
        }
    }

    private static int getGroup(String number) {

        int lastTwo = Integer.parseInt(number.substring(2));

        return (lastTwo - 1) / 4 + 1;
    }
}