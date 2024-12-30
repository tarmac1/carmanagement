package com.example.car_management.controller;

import com.example.car_management.dto.CreateGarageDTO;
import com.example.car_management.dto.UpdateGarageDTO;
import com.example.car_management.model.ServiceCenter;
import com.example.car_management.service.ServiceCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/service-centers")
public class ServiceCenterController {

    @Autowired
    private ServiceCenterService serviceCenterService;

    @PostMapping
    public ResponseEntity<ServiceCenter> createServiceCenter(@Valid @RequestBody CreateGarageDTO garageDto) {
        return new ResponseEntity<>(serviceCenterService.createServiceCenter(garageDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenter> getServiceCenterById(@PathVariable Long id) {
        return serviceCenterService.getServiceCenterById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<ServiceCenter>> getAllServiceCenters(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(serviceCenterService.getServiceCentersByCity(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCenter> updateServiceCenter(@PathVariable Long id, @Valid @RequestBody UpdateGarageDTO garageDto) {
        return serviceCenterService.updateServiceCenter(id, garageDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCenter(@PathVariable Long id) {
        return serviceCenterService.deleteServiceCenter(id) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
