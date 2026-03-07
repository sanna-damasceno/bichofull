package com.bichofull.backend.service;

import com.bichofull.backend.dto.DrawRequestDTO;
import com.bichofull.backend.dto.DrawResponseDTO;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.repository.DrawRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

import java.time.LocalDateTime;

@Service
public class DrawService {

    private final DrawRepository drawRepository;

    public DrawService(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }

    public Draw createDraw(DrawRequestDTO request) {

        Draw draw = new Draw();

        draw.setDrawDate(LocalDateTime.now());
        draw.setFirstPrize(request.getFirstPrize());
        draw.setSecondPrize(request.getSecondPrize());
        draw.setThirdPrize(request.getThirdPrize());
        draw.setFourthPrize(request.getFourthPrize());
        draw.setFifthPrize(request.getFifthPrize());

        return drawRepository.save(draw);
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

    public Draw runDraw() {

        Random random = new Random();

        Draw draw = new Draw();

        draw.setDrawDate(LocalDateTime.now());

        draw.setFirstPrize(String.format("%04d", random.nextInt(10000)));
        draw.setSecondPrize(String.format("%04d", random.nextInt(10000)));
        draw.setThirdPrize(String.format("%04d", random.nextInt(10000)));
        draw.setFourthPrize(String.format("%04d", random.nextInt(10000)));
        draw.setFifthPrize(String.format("%04d", random.nextInt(10000)));

        return drawRepository.save(draw);
    }
}