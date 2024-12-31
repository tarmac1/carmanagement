package com.example.car_management.service;

import com.example.car_management.model.Car;
import com.example.car_management.repository.CarRepository;
import com.example.car_management.dto.CreateCarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    public Car createCar(CreateCarDTO carDto) {
        if (carDto == null) {
            throw new IllegalArgumentException("CreateCarDTO cannot be null");
        }
        Car car = new Car();
        car.setBrand(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getProductionYear());
        car.setLicensePlate(carDto.getLicensePlate());
        return carRepository.save(car);
    }
}
