package com.example.car_management.controller;

import com.example.car_management.dto.CreateCarDTO;
import com.example.car_management.dto.ResponseCarDTO;
import com.example.car_management.model.Car;
import com.example.car_management.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<ResponseCarDTO>> getAllCars() {
        List<Car> cars = carService.getAllCars();

        List<ResponseCarDTO> response = cars.stream()
                .map(car -> {
                    ResponseCarDTO dto = new ResponseCarDTO();
                    dto.setId(car.getId());
                    dto.setMake(car.getBrand()); // Map "brand" to "make"
                    dto.setModel(car.getModel());
                    dto.setProductionYear(car.getYear()); // Map "year" to "productionYear"
                    dto.setLicensePlate(car.getLicensePlate());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseCarDTO> createCar(@RequestBody CreateCarDTO carDto) {
        Car createdCar = carService.createCar(carDto);

        ResponseCarDTO responseDto = new ResponseCarDTO();
        responseDto.setId(createdCar.getId());
        responseDto.setMake(createdCar.getBrand());
        responseDto.setModel(createdCar.getModel());
        responseDto.setProductionYear(createdCar.getYear());
        responseDto.setLicensePlate(createdCar.getLicensePlate());

        return ResponseEntity.status(201).body(responseDto); // Return created car with status 201
    }
}
