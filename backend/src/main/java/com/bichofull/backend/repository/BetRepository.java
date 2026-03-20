package com.bichofull.backend.repository;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByStatus(BetStatus status);

    List<Bet> findByUser(User user);

    List<Bet> findByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bet b WHERE b.user.id = :userId AND b.status = :status")
    BigDecimal sumAmountByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BetStatus status);

    List<Bet> findByUserAndStatus(User user, BetStatus status);
}
