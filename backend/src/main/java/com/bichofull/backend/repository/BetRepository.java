package com.bichofull.backend.repository;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByStatus(BetStatus status);
}