package com.bichofull.backend.controller;

import com.bichofull.backend.model.Animal;
import com.bichofull.backend.repository.AnimalRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    // Retornar a lista completa de animais (ex: Avestruz, Águia, etc.) 
    // para que o frontend possa exibir as opções de aposta ao usuário.
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }
}