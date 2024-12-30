package com.example.car_management.controller;

import com.example.car_management.dto.CreateGarageDTO;
import com.example.car_management.model.Garage;
import com.example.car_management.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @PostMapping
    public ResponseEntity<Garage> createGarage(@Valid @RequestBody CreateGarageDTO garageDto) {
        return new ResponseEntity<>(garageService.createGarage(garageDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> getGarageById(@PathVariable Long id) {
        return garageService.getGarageById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Garage>> getAllGarages(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer capacity) {
        return ResponseEntity.ok(garageService.getGaragesByFilters(city, capacity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garage> updateGarage(@PathVariable Long id, @Valid @RequestBody CreateGarageDTO garageDto) {
        return garageService.updateGarage(id, garageDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        return garageService.deleteGarage(id) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
