package com.example.car_management.service;

import com.example.car_management.model.Car;
import com.example.car_management.repository.CarRepository;
import com.example.car_management.dto.CreateCarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;


    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    public Car createCar(CreateCarDTO carDto) {
        Car car = new Car();
        car.setBrand(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getProductionYear());

        return carRepository.save(car);
    }
}
