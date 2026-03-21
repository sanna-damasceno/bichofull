package com.bichofull.backend.service;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.*;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BetProcessorServiceTest {

    private BetRepository betRepository;
    private UserRepository userRepository;
    private BetProcessorService betProcessorService;

    @BeforeEach
    void setup() {
        betRepository = mock(BetRepository.class);
        userRepository = mock(UserRepository.class);

        betProcessorService = new BetProcessorService(betRepository, userRepository);
    }

    @Test
    void shouldMarkBetAsWon() {

        User user = new User();
        user.setBalance(BigDecimal.ZERO);

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setAmount(BigDecimal.valueOf(10));
        bet.setChosenNumber("1234");
        bet.setStatus(BetStatus.PENDING);
        bet.setUser(user);

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatusWithUser(BetStatus.PENDING))
                .thenReturn(List.of(bet));

        betProcessorService.processBets(draw);

        assertEquals(BetStatus.WON, bet.getStatus());
        assertNotNull(bet.getPrize());

        verify(betRepository).saveAll(anyList());
        verify(userRepository).saveAll(anyList());
    }

    @Test
    void shouldMarkBetAsLost() {

        User user = new User();
        user.setBalance(BigDecimal.ZERO);

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setAmount(BigDecimal.valueOf(10));
        bet.setChosenNumber("9999");
        bet.setStatus(BetStatus.PENDING);
        bet.setUser(user);

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatusWithUser(BetStatus.PENDING))
                .thenReturn(List.of(bet));

        betProcessorService.processBets(draw);

        assertEquals(BetStatus.LOST, bet.getStatus());

        verify(betRepository).saveAll(anyList());
        verify(userRepository).saveAll(anyList());
    }

    @Test
    void shouldCalculatePrizeForWinner() {

        User user = new User();
        user.setBalance(BigDecimal.ZERO);

        Bet bet = new Bet();
        bet.setType(BetType.THOUSAND);
        bet.setAmount(BigDecimal.valueOf(10));
        bet.setChosenNumber("1234");
        bet.setStatus(BetStatus.PENDING);
        bet.setUser(user);

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatusWithUser(BetStatus.PENDING))
                .thenReturn(List.of(bet));

        betProcessorService.processBets(draw);

        assertNotNull(bet.getPrize());

        verify(betRepository).saveAll(anyList());
        verify(userRepository).saveAll(anyList());
    }

    @Test
    void shouldDoNothingWhenNoBets() {

        Draw draw = new Draw();
        draw.setFirstPrize("1234");

        when(betRepository.findByStatusWithUser(BetStatus.PENDING))
                .thenReturn(List.of());

        betProcessorService.processBets(draw);

        verify(betRepository, never()).saveAll(anyList());
        verify(userRepository, never()).saveAll(anyList());
    }
}