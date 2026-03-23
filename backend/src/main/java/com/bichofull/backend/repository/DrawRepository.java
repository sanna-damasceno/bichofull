package com.bichofull.backend.repository;

import com.bichofull.backend.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DrawRepository extends JpaRepository<Draw, Long> {
    

    Draw findTopByOrderByDrawDateDesc();

    List<Draw> findTop20ByOrderByDrawDateDesc();
}