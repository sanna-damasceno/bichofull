package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;
import java.util.List;


@Service
public class BetProcessorService {

    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public BetProcessorService(BetRepository betRepository,
                               UserRepository userRepository) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
    }

    @Async
    @Transactional
    public void processBets(Draw draw) {

        List<Bet> bets = betRepository.findByStatusWithUser(BetStatus.PENDING);

        if (bets.isEmpty()) {
            System.out.println("No pending bets to process.");
            return;
        }

        System.out.println("Processing " + bets.size() + " bets...");

        for (Bet bet : bets) {

            boolean winner = BetChecker.isWinner(bet, draw.getFirstPrize());

            if (winner) {

                BigDecimal prize = PrizeCalculator.calculatePrize(
                        bet.getType(),
                        bet.getAmount()
                );

                bet.setStatus(BetStatus.WON);
                bet.setPrize(prize);

                // atualiza saldo
                User user = bet.getUser();
                user.setBalance(user.getBalance().add(prize));

            } else {
                bet.setStatus(BetStatus.LOST);
            }

            bet.setDraw(draw);
        }

        // salva tudo de uma vez
        betRepository.saveAll(bets);

        // salva usuários sem duplicar
        userRepository.saveAll(
            bets.stream()
                .map(Bet::getUser)
                .distinct()
                .toList()
        );

        System.out.println("Bet processing finished.");
    }
}