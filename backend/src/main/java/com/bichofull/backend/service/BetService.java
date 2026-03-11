package com.bichofull.backend.service;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BetService {

    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private static final Logger log = LoggerFactory.getLogger(BetService.class);

    public BetService(BetRepository betRepository, UserRepository userRepository,
        AuditService auditService
    ) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
        
    }

    public Bet createBet(BetRequestDTO request, String email) {

        log.info("User {} is attempting to create a bet", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance().compareTo(request.getAmount()) < 0) {

            log.warn("User {} tried to bet with insufficient balance", email);

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
        
        Bet savedBet = betRepository.save(bet);

        log.info("Bet {} created successfully for user {}", savedBet.getId(), email);

        auditService.log(
            "CREATE_BET",
            email,
            "User created bet on number " + savedBet.getChosenNumber()
        );

        return savedBet;
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