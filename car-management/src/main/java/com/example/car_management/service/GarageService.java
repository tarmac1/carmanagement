package com.example.car_management.service;

import com.example.car_management.dto.CreateGarageDTO;
import com.example.car_management.model.Garage;
import com.example.car_management.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;

    public Garage createGarage(CreateGarageDTO garageDto) {
        Garage garage = new Garage();
        garage.setName(garageDto.getName());
        garage.setLocation(garageDto.getLocation());
        garage.setCity(garageDto.getCity());
        garage.setCapacity(garageDto.getCapacity());
        return garageRepository.save(garage);
    }

    public Optional<Garage> getGarageById(Long id) {
        return garageRepository.findById(id);
    }

    public List<Garage> getGaragesByFilters(String city, Integer capacity) {
        if (city != null && capacity != null) {
            return garageRepository.findByCityAndCapacity(city, capacity);
        } else if (city != null) {
            return garageRepository.findByCity(city);
        } else if (capacity != null) {
            return garageRepository.findByCapacity(capacity);
        } else {
            return garageRepository.findAll();
        }
    }

    public Optional<Garage> updateGarage(Long id, CreateGarageDTO garageDto) {
        Optional<Garage> existingGarage = garageRepository.findById(id);
        if (existingGarage.isPresent()) {
            Garage garage = existingGarage.get();
            garage.setName(garageDto.getName());
            garage.setLocation(garageDto.getLocation());
            garage.setCity(garageDto.getCity());
            garage.setCapacity(garageDto.getCapacity());
            return Optional.of(garageRepository.save(garage));
        }
        return Optional.empty();
    }

    public boolean deleteGarage(Long id) {
        if (garageRepository.existsById(id)) {
            garageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
