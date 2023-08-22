package com.vehiclemanagement.service;
import com.vehiclemanagement.entity.Vehicle;
import java.util.List;

public interface VehicleService {
    List<Vehicle> getVehicles();

    Vehicle getVehicleById(long vehicleId);

    Vehicle addVehicle(Vehicle vehicle);

    Vehicle updateVehicle(Vehicle vehicle);

    void deleteVehicleById(long vehicleId);
}
