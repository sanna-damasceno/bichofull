package com.bichofull.backend.service;

import com.bichofull.backend.dto.DrawRequestDTO;
import com.bichofull.backend.dto.DrawResponseDTO;
import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.repository.DrawRepository;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DrawService {

    private final DrawRepository drawRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final BetProcessorService betProcessorService;
    private static final Logger log = LoggerFactory.getLogger(DrawService.class);

    public DrawService(DrawRepository drawRepository,
                       BetRepository betRepository,
                       UserRepository userRepository,
                       BetProcessorService betProcessorService
    ) {
        this.drawRepository = drawRepository;
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        this.betProcessorService = betProcessorService;
    }

    public Draw createDraw(DrawRequestDTO request) {

        Draw draw = new Draw();

        draw.setDrawDate(LocalDateTime.now());
        draw.setFirstPrize(request.getFirstPrize());
        draw.setSecondPrize(request.getSecondPrize());
        draw.setThirdPrize(request.getThirdPrize());
        draw.setFourthPrize(request.getFourthPrize());
        draw.setFifthPrize(request.getFifthPrize());

        Draw saved = drawRepository.save(draw);

        log.info("Sorteio manual criado: {}", saved.getId());

        betProcessorService.processBets(saved);

        return saved;
    }

    public DrawResponseDTO toDTO(Draw draw) {
        return new DrawResponseDTO(
                draw.getId(),
                draw.getDrawDate(),
                draw.getFirstPrize(),
                draw.getSecondPrize(),
                draw.getThirdPrize(),
                draw.getFourthPrize(),
                draw.getFifthPrize()
        );
    }

    @Transactional
    public Draw runDraw() {

        Draw draw = new Draw();

        draw.setDrawDate(LocalDateTime.now());

        Random random = new Random();

        draw.setFirstPrize(String.format("%04d", random.nextInt(10000)));
        draw.setSecondPrize(String.format("%04d", random.nextInt(10000)));
        draw.setThirdPrize(String.format("%04d", random.nextInt(10000)));
        draw.setFourthPrize(String.format("%04d", random.nextInt(10000)));
        draw.setFifthPrize(String.format("%04d", random.nextInt(10000)));

        Draw savedDraw = drawRepository.save(draw);

        log.info("Sorteio criado: {}", savedDraw.getId());

        betProcessorService.processBets(savedDraw);

        return savedDraw;
    }

    public Draw getLastDraw() {
        return drawRepository.findTopByOrderByDrawDateDesc();
    }

    public List<Draw> getDrawHistory() {
        return drawRepository.findTop20ByOrderByDrawDateDesc();
    }


}