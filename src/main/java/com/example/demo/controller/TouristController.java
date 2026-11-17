package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Tourist;
import com.example.demo.repository.TouristRepository;

@RestController
@RequestMapping("/api/tourist")
public class TouristController {

    @Autowired
    private TouristRepository repository;

    @PutMapping("/{id}")
    public Tourist updateTourist(
            @PathVariable int id,
            @RequestHeader("X-REQUEST-TOKEN") String token,
            @RequestBody Tourist newDetails) {

        // Validate request token
        if (!"ABC123".equals(token)) {
            throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Invalid Request Token");
        }

        // Find existing tourist
        Tourist tourist = repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Tourist not found"));

        // Update tourist details
        tourist.setDestination(newDetails.getDestination());
        tourist.setContact(newDetails.getContact());

        // Save updated details
        return repository.save(tourist);
    }
}