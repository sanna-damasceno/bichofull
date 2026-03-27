package com.bichofull.backend.service;

import com.bichofull.backend.dto.BetHistoryDTO;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


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

    private void validateBet(BetRequestDTO request) {

        String number = request.getChosenNumber();

        if (number == null || !number.matches("\\d+")) {
            throw new RuntimeException("A aposta deve conter apenas números");
        }

        switch (request.getType()) {

            case GROUP:
                if (number.length() != 2) {
                    throw new RuntimeException("Grupo deve ter 2 números");
                }

                int grupo = Integer.parseInt(number);

                if (grupo < 1 || grupo > 25) {
                    throw new RuntimeException("Grupo deve estar entre 01 e 25");
                }
                break;
                
            case TEN:
                if (number.length() > 2) {
                    throw new RuntimeException("Grupo/Dezena deve ter no máximo 2 números");
                }
                break;

            case THOUSAND:
                if (number.length() != 4) {
                    throw new RuntimeException("Milhar deve ter exatamente 4 números");
                }
                break;
        }
    }

    public Bet createBet(BetRequestDTO request, String email) {

        validateBet(request);

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

    public BigDecimal sumAmountByUserAndStatus(Long userId, BetStatus status) {
        BigDecimal sum = betRepository.sumAmountByUserIdAndStatus(userId, status);
        return sum != null ? sum : BigDecimal.ZERO;
    }

    
    public BigDecimal calculateTotalPendingPrize(Long userId) {
        System.out.println("🔥 CALCULANDO PENDING CORRETAMENTE");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Busca todas as apostas com status PENDING deste usuário
        List<Bet> pendingBets = betRepository.findByUserAndStatus(user, BetStatus.PENDING);

        // Soma o prêmio potencial de cada uma usando sua PrizeCalculator
        return pendingBets.stream()
                .map(bet -> {
                BigDecimal prize = PrizeCalculator.calculatePrize(bet.getType(), bet.getAmount());
                System.out.println("Tipo: " + bet.getType() + " → " + prize);
                return prize;
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Object> getUserHistorySummary(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Bet> allBets = betRepository.findByUserOrderByCreatedAtDesc(user);

        long totalBets = allBets.size();
        long wins = allBets.stream().filter(b -> b.getStatus() == BetStatus.WON).count();
        
        // Calcula a Taxa de Acerto
        double winRate = totalBets > 0 ? (double) wins / totalBets * 100 : 0;

        // Soma o total que ele já ganhou (prize das apostas WON)
        BigDecimal totalWon = allBets.stream()
                .filter(b -> b.getStatus() == BetStatus.WON && b.getPrize() != null)
                .map(Bet::getPrize)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Soma o total que ele perdeu (amount das apostas LOST)
        BigDecimal totalLost = allBets.stream()
                .filter(b -> b.getStatus() == BetStatus.LOST)
                .map(Bet::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Converte a lista para BetHistoryDTO
        List<BetHistoryDTO> history = allBets.stream()
                .map(b -> new BetHistoryDTO(b.getId(), b.getType(), b.getChosenNumber(), b.getAmount(), b.getStatus(), b.getPrize(), b.getCreatedAt()))
                .toList();

        return Map.of(
            "totalBets", totalBets,
            "winRate", Math.round(winRate),
            "totalWon", totalWon,
            "totalLost", totalLost,
            "history", history
        );
    }

    public BigDecimal sumPrizeByUserAndStatus(Long userId, BetStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return betRepository.findByUserAndStatus(user, status).stream()
                .map(b -> b.getPrize() != null ? b.getPrize() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
        

}