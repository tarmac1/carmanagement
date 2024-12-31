package com.example.car_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UpdateCarDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String make;

    @NotNull
    @Size(min = 1, max = 50)
    private String model;

    @NotNull
    private Integer productionYear;

    @NotNull
    @Size(min = 1, max = 20)
    private String licensePlate;

    @NotNull
    @Size(min = 1, message = "At least one garage ID must be provided")
    private List<Long> garageIds;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<Long> getGarageIds() {
        return garageIds;
    }

    public void setGarageIds(List<Long> garageIds) {
        this.garageIds = garageIds;
    }
}
