package com.example.car_management.repository;

import com.example.car_management.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brand);
    List<Car> findByBrandAndYearBetween(String brand, Integer fromYear, Integer toYear);
}
