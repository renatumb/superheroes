package com.example.superheroes.antiHero.service;

import com.example.superheroes.antiHero.entity.AntiHeroEntity;
import com.example.superheroes.antiHero.repository.AntiHeroRepository;
import com.example.superheroes.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AntiHeroService {


    private final AntiHeroRepository antiHeroRepository;

    public Iterable<AntiHeroEntity> findAllHeroes() {
        return antiHeroRepository.findAll();
    }

    public AntiHeroEntity findAntiHeroById(UUID uuid) {
        return findOrThrow(uuid);
    }

    public void removeAntiHeroById(UUID uuid) {
        antiHeroRepository.deleteById(uuid);
    }

    public AntiHeroEntity addAntiHero(AntiHeroEntity antiHeroEntity) {
        return antiHeroRepository.save(antiHeroEntity);
    }

    public AntiHeroEntity updateAntiHero(UUID uuid, AntiHeroEntity antiHeroEntity) {
        findOrThrow(uuid);
        return antiHeroRepository.save(antiHeroEntity);
    }

    private AntiHeroEntity findOrThrow(final UUID uuid) {
        return antiHeroRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Anti-Hero by ID: " + uuid + " not found"));
    }
}
