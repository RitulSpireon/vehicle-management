package com.vehiclemanagement.controller;

import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Get a list of all vehicles
    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles() {
        try {
            return vehicleService.getVehicles();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get a specific vehicle by its ID
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long vehicleId) {
        System.out.println(vehicleId);
        try {
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            System.out.printf(vehicle.toString());
            if (vehicle != null) {
                return new ResponseEntity<>(vehicle, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a new vehicle
    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle addedVehicle = vehicleService.addVehicle(vehicle);
            if(addedVehicle == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(addedVehicle, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a vehicle by its ID
    @DeleteMapping("/vehicles/{vehicleId}")
    public ResponseEntity<HttpStatus> deleteVehicleById(@PathVariable long vehicleId){
        try{
            vehicleService.deleteVehicleById(vehicleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
