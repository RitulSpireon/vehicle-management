package com.vehiclemanagement.service;

import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImp implements VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    // Retrieve a list of all vehicles from the repository
    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepo.findAll();
    }

    // Retrieve a specific vehicle by its ID from the repository
    @Override
    public Vehicle getVehicleById(long vehicleId) {
        return vehicleRepo.findById(vehicleId).get();
    }

    // Add a new vehicle to the repository
    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    // Update an existing vehicle in the repository
    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    // Delete a vehicle by its ID from the repository
    // Throws a runtime exception if the vehicle's service status is "Done"
    @Override
    public void deleteVehicleById(long vehicleId) {
        Vehicle vehicle = vehicleRepo.getOne(vehicleId);
        if ("Done".equalsIgnoreCase(vehicle.getServiceStatus())) {
            vehicleRepo.delete(vehicle);
        } else {
            throw new RuntimeException("Cannot delete a vehicle without service status 'Done'.");
        }


    }
}
