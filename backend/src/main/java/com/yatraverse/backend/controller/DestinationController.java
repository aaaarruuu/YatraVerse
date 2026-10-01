package com.yatraverse.backend.controller;

import com.yatraverse.backend.model.Destination;
import com.yatraverse.backend.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationRepository destinationRepository;

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Destination getDestination(@PathVariable Long id) {
        return destinationRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Destination not found"));
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationRepository.save(destination);
    }

    @PutMapping("/{id}")
    public Destination updateDestination(@PathVariable Long id, @RequestBody Destination update) {
        Destination existing = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        if (update.getName() != null) existing.setName(update.getName());
        if (update.getLocation() != null) existing.setLocation(update.getLocation());
        if (update.getDescription() != null) existing.setDescription(update.getDescription());
        if (update.getImageUrl() != null) existing.setImageUrl(update.getImageUrl());
        return destinationRepository.save(existing);
    }
}