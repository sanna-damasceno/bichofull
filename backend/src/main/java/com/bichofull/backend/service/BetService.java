package com.bichofull.backend.service;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BetService {

    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public BetService(BetRepository betRepository, UserRepository userRepository) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
    }

    public Bet createBet(BetRequestDTO request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        user.setBalance(user.getBalance().subtract(request.getAmount()));

        Bet bet = Bet.builder()
                .type(request.getType())
                .chosenNumber(request.getChosenNumber())
                .amount(request.getAmount())
                .status(BetStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return betRepository.save(bet);
    }

    public BetResponseDTO createBetDTO(BetRequestDTO request, String email) {

        Bet bet = createBet(request, email);

        return toDTO(bet);
    }

    public BetResponseDTO toDTO(Bet bet) {
        return new BetResponseDTO(
                bet.getId(),
                bet.getType(),
                bet.getChosenNumber(),
                bet.getAmount(),
                bet.getStatus(),
                bet.getPrize(),
                bet.getCreatedAt()
        );
    }

    public List<BetResponseDTO> getUserBetsDTO(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Bet> bets = betRepository.findByUserOrderByCreatedAtDesc(user);

        return bets.stream()
                .map(bet -> BetResponseDTO.builder()
                        .id(bet.getId())
                        .type(bet.getType())
                        .chosenNumber(bet.getChosenNumber())
                        .amount(bet.getAmount())
                        .status(bet.getStatus())
                        .prize(bet.getPrize())
                        .createdAt(bet.getCreatedAt())
                        .build())
                .toList();
    }
}