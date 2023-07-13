package com.example.superheroes.antiHero.controller;

import com.example.superheroes.antiHero.dto.AntiHeroDTO;
import com.example.superheroes.antiHero.entity.AntiHeroEntity;
import com.example.superheroes.antiHero.service.AntiHeroService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/anti-heroes")
public class AntiHeroController {

    private final AntiHeroService antiHeroService;
    private final ModelMapper modelMapper;

    private AntiHeroDTO convertToDTO(AntiHeroEntity antiHeroEntity) {
        return modelMapper.map(antiHeroEntity, AntiHeroDTO.class);
    }

    private AntiHeroEntity converDtoToEntity(AntiHeroDTO antiHeroDTO) {
        return modelMapper.map(antiHeroDTO, AntiHeroEntity.class);
    }


    @GetMapping
    public List<AntiHeroEntity> getAntiHeroes() {
        return StreamSupport.stream(antiHeroService
                        .findAllHeroes()
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AntiHeroDTO getAntiHeroById(@PathVariable("id") UUID uuid) {
        return convertToDTO(antiHeroService.findAntiHeroById(uuid));
    }

    @PostMapping
    public AntiHeroEntity postAntiHero(@Valid @RequestBody AntiHeroDTO antiHeroDTO) {
        AntiHeroEntity antiHeroEntity = converDtoToEntity(antiHeroDTO);
        return antiHeroService.addAntiHero(antiHeroEntity);
    }

    @PutMapping("/{id}")
    public void updateAntiHero(@PathVariable("id") UUID uuid, @Valid @RequestBody AntiHeroDTO antiHeroDTO) {
        if (uuid != antiHeroDTO.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID DOES NOT MATCH");
        }
        antiHeroService.updateAntiHero(uuid, converDtoToEntity(antiHeroDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteAntiHeroById(@PathVariable("id") UUID uuid) {
        antiHeroService.removeAntiHeroById(uuid);
    }
}
