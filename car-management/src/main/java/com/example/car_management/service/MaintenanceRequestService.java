package com.example.car_management.service;

import com.example.car_management.dto.CreateMaintenanceDTO;
import com.example.car_management.dto.UpdateMaintenanceDTO;
import com.example.car_management.model.MaintenanceRequest;
import com.example.car_management.repository.MaintenanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRequestService {

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    public MaintenanceRequest createMaintenanceRequest(CreateMaintenanceDTO requestDto) {
        MaintenanceRequest request = new MaintenanceRequest();

        request.setCarId(requestDto.getCarId());
        request.setServiceCenterId(requestDto.getGarageId());


        try {
            LocalDate scheduledDate = LocalDate.parse(requestDto.getScheduledDate());
            request.setRequestDate(scheduledDate);
        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException("Invalid date format for scheduledDate. Expected format: yyyy-MM-dd");
        }


        request.setServiceType(requestDto.getServiceType());


        return maintenanceRequestRepository.save(request);
    }


    public Optional<MaintenanceRequest> updateMaintenanceRequest(Long id, UpdateMaintenanceDTO requestDto) {
        Optional<MaintenanceRequest> existingRequest = maintenanceRequestRepository.findById(id);
        if (existingRequest.isPresent()) {
            MaintenanceRequest request = existingRequest.get();
            if (requestDto.getServiceType() != null) request.setServiceType(requestDto.getServiceType());
            if (requestDto.getScheduledDate() != null) {
                try {
                    LocalDate scheduledDate = LocalDate.parse(requestDto.getScheduledDate());  // Convert string to LocalDate
                    request.setRequestDate(scheduledDate);  // Set the new date
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format for scheduledDate. Expected format: yyyy-MM-dd");
                }
            }
            return Optional.of(maintenanceRequestRepository.save(request));
        }
        return Optional.empty();
    }

    public Optional<MaintenanceRequest> getMaintenanceRequestById(Long id) {
        return maintenanceRequestRepository.findById(id);
    }

    public List<MaintenanceRequest> getFilteredMaintenanceRequests(Long carId, Long garageId, String startDate, String endDate) {
        LocalDate start = null;
        LocalDate end = null;

        try {
            if (startDate != null && !startDate.isEmpty()) {
                start = LocalDate.parse(startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                end = LocalDate.parse(endDate);
            }
        } catch (DateTimeParseException e) {
            return List.of();  // Returning empty list if date parsing fails
        }

        if (carId != null && garageId != null && start != null && end != null) {
            return maintenanceRequestRepository.findByCarIdAndServiceCenterIdAndRequestDateBetween(carId, garageId, start, end);
        } else if (carId != null && garageId != null) {
            return maintenanceRequestRepository.findByCarIdAndServiceCenterId(carId, garageId);
        } else if (carId != null) {
            return maintenanceRequestRepository.findByCarId(carId);
        } else if (garageId != null) {
            return maintenanceRequestRepository.findByServiceCenterId(garageId);
        } else {
            return maintenanceRequestRepository.findAll();  // No filters, return all
        }
    }

    public List<MaintenanceRequest> getMonthlyRequestsReport(Long garageId, String startMonth, String endMonth) {
        LocalDate start = LocalDate.parse(startMonth + "-01");
        LocalDate end = LocalDate.parse(endMonth + "-01").plusMonths(1);  // End date goes to the next month
        return maintenanceRequestRepository.findByServiceCenterIdAndRequestDateBetween(
                garageId,
                start,
                end
        );
    }

    public List<MaintenanceRequest> getDailyAvailabilityReport(Long garageId, String startDate, String endDate) {
        return maintenanceRequestRepository.findByServiceCenterIdAndRequestDateBetween(
                garageId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}
