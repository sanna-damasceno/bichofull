package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BetProcessorServiceTest {

    private BetRepository betRepository;
    private BetProcessorService betProcessorService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        betRepository = Mockito.mock(BetRepository.class);
        betProcessorService = new BetProcessorService(betRepository, userRepository);
    }

    // ✅ Aposta vencedora
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

        verify(betRepository).saveAll(anyList());

        assertEquals(BetStatus.WON, bet.getStatus());
    }

    // ✅ Aposta perdedora
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

        verify(betRepository).saveAll(anyList());

        assertEquals(BetStatus.LOST, bet.getStatus());
    }

    // ✅ Prêmio calculado corretamente
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

        assertNotNull(bet.getPrize());
        assertTrue(bet.getPrize().compareTo(BigDecimal.ZERO) > 0);
    }

    // ✅ Caso não tenha apostas
    @Test
    void shouldDoNothingWhenNoBets() {

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatus(BetStatus.PENDING))
                .thenReturn(List.of());

        betProcessorService.processBets(draw);

        verify(betRepository, never()).saveAll(anyList());
    }

    // 🚀 Teste mais robusto (valida conteúdo salvo)
    @Test
    void shouldSaveUpdatedBetsCorrectly() {

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

        verify(betRepository).saveAll(argThat(iterable -> {
            List<Bet> list = new ArrayList<>();
            iterable.forEach(list::add);

            return list.size() == 1 &&
                list.get(0).getStatus() == BetStatus.WON;
        }));
    }
}