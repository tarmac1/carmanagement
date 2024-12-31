package com.example.car_management.service;

import com.example.car_management.dto.CreateGarageDTO;
import com.example.car_management.dto.UpdateGarageDTO;
import com.example.car_management.model.ServiceCenter;
import com.example.car_management.repository.ServiceCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCenterService {

    @Autowired
    private ServiceCenterRepository serviceCenterRepository;
    public ServiceCenter createServiceCenter(CreateGarageDTO garageDto) {
        ServiceCenter serviceCenter = new ServiceCenter();
        serviceCenter.setName(garageDto.getName());
        serviceCenter.setCity(garageDto.getCity());
        serviceCenter.setCapacity(garageDto.getCapacity());
        serviceCenter.setLocation(garageDto.getLocation());
        return serviceCenterRepository.save(serviceCenter);
    }

    public Optional<ServiceCenter> updateServiceCenter(Long id, UpdateGarageDTO garageDto) {
        Optional<ServiceCenter> existingServiceCenter = serviceCenterRepository.findById(id);
        if (existingServiceCenter.isPresent()) {
            ServiceCenter serviceCenter = existingServiceCenter.get();
            if (garageDto.getName() != null) serviceCenter.setName(garageDto.getName());
            if (garageDto.getCity() != null) serviceCenter.setCity(garageDto.getCity());
            if (garageDto.getCapacity() != null) serviceCenter.setCapacity(garageDto.getCapacity());
            if (garageDto.getLocation() != null) serviceCenter.setLocation(garageDto.getLocation());
            return Optional.of(serviceCenterRepository.save(serviceCenter));
        }
        return Optional.empty();
    }

    public boolean deleteServiceCenter(Long id) {
        if (serviceCenterRepository.existsById(id)) {
            serviceCenterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ServiceCenter> getServiceCenterById(Long id) {
        return serviceCenterRepository.findById(id);
    }

    public List<ServiceCenter> getAllServiceCenters() {
        return serviceCenterRepository.findAll();
    }

    public List<ServiceCenter> getServiceCentersByCity(String city) {
        return serviceCenterRepository.findByCity(city);
    }
}
