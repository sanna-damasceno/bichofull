package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.repository.BetRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.List;

@Service
public class BetProcessorService {

    private final BetRepository betRepository;

    public BetProcessorService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public void processBets(Draw draw) {

        List<Bet> bets = betRepository.findByStatus(BetStatus.PENDING);

        for (Bet bet : bets) {

            boolean winner = BetChecker.isWinner(bet, draw.getFirstPrize());

            if (winner) {

                BigDecimal prize = PrizeCalculator.calculatePrize(
                        bet.getType(),
                        bet.getAmount()
                );

                bet.setStatus(BetStatus.WON);
                bet.setPrize(prize);

            } else {

                bet.setStatus(BetStatus.LOST);
            }

            betRepository.save(bet);
        }
    }
}