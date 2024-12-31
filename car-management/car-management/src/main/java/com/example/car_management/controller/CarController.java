package com.example.car_management.controller;

import com.example.car_management.dto.CreateCarDTO;
import com.example.car_management.dto.ResponseCarDTO;
import com.example.car_management.model.Car;
import com.example.car_management.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<ResponseCarDTO>> getAllCars() {
        logger.info("Fetching all cars");

        List<Car> cars = carService.getAllCars();

        List<ResponseCarDTO> response = cars.stream()
                .map(car -> {
                    ResponseCarDTO dto = new ResponseCarDTO();
                    dto.setId(car.getId());
                    dto.setMake(car.getBrand());
                    dto.setModel(car.getModel());
                    dto.setProductionYear(car.getYear());
                    dto.setLicensePlate(car.getLicensePlate());
                    return dto;
                })
                .collect(Collectors.toList());

        logger.info("Successfully fetched {} cars", response.size());
        return ResponseEntity.ok(response); // Return empty list if no cars exist
    }

    @PostMapping
    public ResponseEntity<ResponseCarDTO> createCar(@RequestBody CreateCarDTO carDto) {
        logger.info("Creating a new car: {}", carDto);

        try {
            Car createdCar = carService.createCar(carDto);

            ResponseCarDTO responseDto = new ResponseCarDTO();
            responseDto.setId(createdCar.getId());
            responseDto.setMake(createdCar.getBrand());
            responseDto.setModel(createdCar.getModel());
            responseDto.setProductionYear(createdCar.getYear());
            responseDto.setLicensePlate(createdCar.getLicensePlate());

            logger.info("Successfully created car with ID: {}", createdCar.getId());
            return ResponseEntity.status(201).body(responseDto); // Return created car with status 201
        } catch (IllegalArgumentException e) {
            logger.error("Error creating car: {}", e.getMessage());
            return ResponseEntity.badRequest().build(); // Handle invalid input
        }
    }
}
