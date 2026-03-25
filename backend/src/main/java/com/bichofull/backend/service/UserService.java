package com.bichofull.backend.service;

import com.bichofull.backend.dto.UserBalanceResponseDTO;
import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserBalanceResponseDTO getBalance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserBalanceResponseDTO(user.getBalance());
    }

    public UserResponseDTO buildUserResponse(User user, List<Bet> bets) {

        BigDecimal totalWon = bets.stream()
                .filter(b -> b.getStatus() == BetStatus.WON)
                .map(b -> b.getPrize() != null ? b.getPrize() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLost = bets.stream()
                .filter(b -> b.getStatus() == BetStatus.LOST)
                .map(Bet::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPending = bets.stream()
                .filter(b -> b.getStatus() == BetStatus.PENDING)
                .map(b -> b.getAmount().multiply(getMultiplier(b.getType())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance(),
                totalWon,
                totalLost,
                totalPending
        );
    }

    private BigDecimal getMultiplier(BetType type) {
        return switch (type) {
            case GROUP -> BigDecimal.valueOf(18);
            case TEN -> BigDecimal.valueOf(60);
            case THOUSAND -> BigDecimal.valueOf(4000);
        };
    }
}