package com.bichofull.backend.service;

import com.bichofull.backend.dto.DrawRequestDTO;
import com.bichofull.backend.dto.DrawResponseDTO;
import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.DrawRepository;
import com.bichofull.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.Random;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DrawService {

    private final DrawRepository drawRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public DrawService(DrawRepository drawRepository,
                       BetRepository betRepository,
                       UserRepository userRepository
    ) {
        this.drawRepository = drawRepository;
        this.betRepository = betRepository;
        this.userRepository = userRepository;
    }

    public Draw createDraw(DrawRequestDTO request) {

        Draw draw = new Draw();

        draw.setDrawDate(LocalDateTime.now());
        draw.setFirstPrize(request.getFirstPrize());
        draw.setSecondPrize(request.getSecondPrize());
        draw.setThirdPrize(request.getThirdPrize());
        draw.setFourthPrize(request.getFourthPrize());
        draw.setFifthPrize(request.getFifthPrize());

        return drawRepository.save(draw);
    }

    public DrawResponseDTO toDTO(Draw draw) {
        return new DrawResponseDTO(
                draw.getId(),
                draw.getDrawDate(),
                draw.getFirstPrize(),
                draw.getSecondPrize(),
                draw.getThirdPrize(),
                draw.getFourthPrize(),
                draw.getFifthPrize()
        );
    }

    public Draw runDraw() {

        Random random = new Random();

        Draw draw = new Draw();

        draw.setDrawDate(LocalDateTime.now());

        draw.setFirstPrize(String.format("%04d", random.nextInt(10000)));
        draw.setSecondPrize(String.format("%04d", random.nextInt(10000)));
        draw.setThirdPrize(String.format("%04d", random.nextInt(10000)));
        draw.setFourthPrize(String.format("%04d", random.nextInt(10000)));
        draw.setFifthPrize(String.format("%04d", random.nextInt(10000)));

        draw = drawRepository.save(draw);

        var bets = betRepository.findByStatus(BetStatus.PENDING);

        for (Bet bet : bets) {

            if (BetChecker.isWinner(bet, draw.getFirstPrize())) {

                bet.setStatus(BetStatus.WON);

                BigDecimal prize = PrizeCalculator.calculatePrize(
                        bet.getType(), 
                        bet.getAmount()
                );

                User user = bet.getUser();
                
                user.setBalance(user.getBalance().add(prize));

                userRepository.save(user);

            } else {

                bet.setStatus(BetStatus.LOST);
            }

            bet.setDraw(draw);

            betRepository.save(bet);
        }

        return draw;
    }


}