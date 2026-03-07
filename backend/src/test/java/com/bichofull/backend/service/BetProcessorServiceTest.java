package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.*;
import com.bichofull.backend.repository.BetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

class BetProcessorServiceTest {

    private BetRepository betRepository;
    private BetProcessorService betProcessorService;

    @BeforeEach
    void setup() {
        betRepository = Mockito.mock(BetRepository.class);
        betProcessorService = new BetProcessorService(betRepository);
    }

    // Aposta vencedora
    @Test
    void shouldMarkBetAsWon() {

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setAmount(BigDecimal.valueOf(10));
        bet.setChosenNumber("1234");
        bet.setStatus(BetStatus.PENDING);

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatus(BetStatus.PENDING))
                .thenReturn(List.of(bet));

        betProcessorService.processBets(draw);

        verify(betRepository).save(bet);

        assert bet.getStatus() == BetStatus.WON;
    }

    //Aposta perdedora
    @Test
    void shouldMarkBetAsLost() {

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setAmount(BigDecimal.valueOf(10));
        bet.setChosenNumber("9999");
        bet.setStatus(BetStatus.PENDING);

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatus(BetStatus.PENDING))
                .thenReturn(List.of(bet));

        betProcessorService.processBets(draw);

        verify(betRepository).save(bet);

        assert bet.getStatus() == BetStatus.LOST;
    }

    // Prêmio calculado
    @Test
    void shouldCalculatePrizeForWinner() {

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setAmount(BigDecimal.valueOf(10));
        bet.setChosenNumber("1234");
        bet.setStatus(BetStatus.PENDING);

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatus(BetStatus.PENDING))
                .thenReturn(List.of(bet));

        betProcessorService.processBets(draw);

        assert bet.getPrize() != null;
    }
}