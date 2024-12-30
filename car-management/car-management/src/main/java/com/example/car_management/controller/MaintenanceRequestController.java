package com.example.car_management.controller;

import com.example.car_management.dto.CreateMaintenanceDTO;
import com.example.car_management.dto.UpdateMaintenanceDTO;
import com.example.car_management.model.MaintenanceRequest;
import com.example.car_management.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;

    @PostMapping
    public ResponseEntity<MaintenanceRequest> createMaintenanceRequest(@Valid @RequestBody CreateMaintenanceDTO requestDto) {
        return ResponseEntity.ok(maintenanceRequestService.createMaintenanceRequest(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRequest> updateMaintenanceRequest(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMaintenanceDTO requestDto) {
        return maintenanceRequestService.updateMaintenanceRequest(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRequest> getMaintenanceRequestById(@PathVariable Long id) {
        return maintenanceRequestService.getMaintenanceRequestById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRequest>> getAllMaintenanceRequests(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) Long garageId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        return ResponseEntity.ok(maintenanceRequestService.getFilteredMaintenanceRequests(carId, garageId, startDate, endDate));
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<List<MaintenanceRequest>> getMonthlyRequestsReport(
            @RequestParam Long garageId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") String startMonth,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") String endMonth) {
        return ResponseEntity.ok(maintenanceRequestService.getMonthlyRequestsReport(garageId, startMonth, endMonth));
    }

    @GetMapping("/daily-availability")
    public ResponseEntity<List<MaintenanceRequest>> getDailyAvailabilityReport(
            @RequestParam Long garageId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        return ResponseEntity.ok(maintenanceRequestService.getDailyAvailabilityReport(garageId, startDate, endDate));
    }
}
