package com.vehiclemanagement.service;

import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.exception.VehicleDeleteException;
import com.vehiclemanagement.exception.VehicleNotFoundException;
import com.vehiclemanagement.exception.VehicleUpdateException;
import com.vehiclemanagement.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Vehicle> vehicleOptional = vehicleRepo.findById(vehicleId);
        if (vehicleOptional.isPresent()) {
            return vehicleOptional.get();
        } else {
            throw new VehicleNotFoundException("Vehicle not found with ID: " + vehicleId);
        }
    }

    // Add a new vehicle to the repository
    @Override
    public Vehicle addVehicle(Vehicle vehicle){
            return vehicleRepo.save(vehicle);
    }

    // Update an existing vehicle in the repository
    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        Optional<Vehicle> vehicleOptional = vehicleRepo.findById(vehicle.getId());
        if (vehicleOptional.isPresent()) {
            return vehicleRepo.save(vehicle);
        }
        else {
            throw new VehicleUpdateException("Vehicle is not present");
        }
    }

    // Delete a vehicle by its ID from the repository
    // Throws a VehicleDeleteException if the vehicle's service status is "Done"
    @Override
    public void deleteVehicleById(long vehicleId) {
        Vehicle vehicle = vehicleRepo.getOne(vehicleId);
        if ("Done".equalsIgnoreCase(vehicle.getServiceStatus())) {
            vehicleRepo.delete(vehicle);
        } else {
            throw new VehicleDeleteException("Cannot delete a vehicle without service status 'Done'.");
        }

    }
}
