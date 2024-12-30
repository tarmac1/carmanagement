package com.example.car_management.repository;

import com.example.car_management.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Long> {
    List<Garage> findByCity(String city);
    List<Garage> findByCapacity(Integer capacity);
    List<Garage> findByCityAndCapacity(String city, Integer capacity);
}
