package com.example.car_management.repository;

import com.example.car_management.model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    List<MaintenanceRequest> findByCarId(Long carId);
    List<MaintenanceRequest> findByServiceCenterId(Long serviceCenterId);
    List<MaintenanceRequest> findByCarIdAndServiceCenterId(Long carId, Long serviceCenterId);
    List<MaintenanceRequest> findByCarIdAndServiceCenterIdAndRequestDateBetween(Long carId, Long serviceCenterId, LocalDate startDate, LocalDate endDate);
    List<MaintenanceRequest> findByServiceCenterIdAndRequestDateBetween(Long serviceCenterId, LocalDate startDate, LocalDate endDate);
}
