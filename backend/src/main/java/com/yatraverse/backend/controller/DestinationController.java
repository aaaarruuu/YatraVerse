package com.yatraverse.backend.controller;

import com.yatraverse.backend.model.Destination;
import com.yatraverse.backend.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
                .orElseThrow(() -> new RuntimeException("Destination not found"));
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationRepository.save(destination);
    }
}
