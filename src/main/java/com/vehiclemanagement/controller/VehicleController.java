package com.vehiclemanagement.controller;

import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.exception.VehicleAddException;
import com.vehiclemanagement.exception.VehicleNotFoundException;
import com.vehiclemanagement.exception.VehicleServiceException;
import com.vehiclemanagement.exception.VehicleUpdateException;
import com.vehiclemanagement.service.VehicleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    // Get a list of all vehicles
    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles() {
        try {
            return vehicleService.getVehicles();
        } catch (VehicleServiceException e) {
            logger.error("Error getting vehicles: {}", e.getMessage(), e);
            throw new VehicleServiceException("Error getting vehicles: " + e.getMessage());
        }
    }

    // Get a specific vehicle by its ID
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long vehicleId) {
        try {
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle != null) {
                return new ResponseEntity<>(vehicle, HttpStatus.OK);
            } else {
                logger.warn("Vehicle not found with ID: {}", vehicleId);
                throw  new VehicleNotFoundException("Vehicle not found with ID: " + vehicleId);
            }
        } catch (VehicleServiceException e) {
            logger.error("Error getting the vehicle: {}", e.getMessage(), e);
            throw new VehicleServiceException("Error getting the vehicle: " + e.getMessage());
        }
    }

    // Add a new vehicle
    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> addVehicle(@Valid @RequestBody Vehicle vehicle) {
        try {
            Vehicle addedVehicle = vehicleService.addVehicle(vehicle);
            if (addedVehicle != null) {
                return new ResponseEntity<>(addedVehicle, HttpStatus.CREATED);
            } else {
                logger.error("Error adding the vehicle");
                throw new VehicleAddException("Error adding the vehicle");
            }
        } catch (VehicleServiceException e) {
            logger.error("Error adding the vehicle: {}", e.getMessage(), e);
            throw new VehicleServiceException("Error adding the vehicle: " + e.getMessage());
        }
    }

    // Update an existing vehicle
    @PutMapping("/vehicles")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle){
        try {
            Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle);
            if (updatedVehicle != null) {
                return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
            } else {
                logger.error("Error updating the vehicle");
                throw new VehicleUpdateException("Error updating the vehicle");
            }
        } catch (VehicleServiceException e) {
            logger.error("Error updating the vehicle: {}", e.getMessage(), e);
            throw new VehicleServiceException("Error updating the vehicle: " + e.getMessage());
        }
    }

    // Delete a vehicle by its ID
    @DeleteMapping("/vehicles/{vehicleId}")
    public ResponseEntity<HttpStatus> deleteVehicleById(@PathVariable long vehicleId){
        try{
            vehicleService.deleteVehicleById(vehicleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (VehicleNotFoundException e) {
            logger.error("Error deleting the vehicle: {}", e.getMessage(), e);
            throw new VehicleNotFoundException("Error deleting the vehicle: " + e.getMessage());
        }
    }


}
