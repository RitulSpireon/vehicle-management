package com.vehiclemanagement.service;


import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.exception.VehicleDeleteException;
import com.vehiclemanagement.exception.VehicleNotFoundException;
import com.vehiclemanagement.exception.VehicleUpdateException;
import com.vehiclemanagement.repository.VehicleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepo vehicleRepo;

    @InjectMocks
    private VehicleServiceImp vehicleService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getVehiclesTest() {

        List<Vehicle> vehicles = new ArrayList<>();
        when(vehicleRepo.findAll()).thenReturn(vehicles);
        List<Vehicle> result = vehicleService.getVehicles();
        assertEquals(vehicles, result);
    }

    @Test
    public void getVehicleByIdTest() {

        long vehicleId = 1L;
        Vehicle vehicle = Vehicle.builder().ownerName("Test").vehicleName("TestVehicle").vehicleDescription("Oil Change").serviceStatus("Pending").build();
        when(vehicleRepo.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        Vehicle result = vehicleService.getVehicleById(vehicleId);
        assertEquals(vehicle, result);
    }

    @Test
    public void getVehicleByIdNotFoundTest() {

        long vehicleId = 1L;
        when(vehicleRepo.findById(vehicleId)).thenReturn(Optional.empty());
        assertThrows(VehicleNotFoundException.class, () -> vehicleService.getVehicleById(vehicleId));
    }

    @Test
    public void addVehicleTest() {

        Vehicle vehicle = Vehicle.builder().ownerName("Test").vehicleName("TestVehicle").vehicleDescription("Oil Change").serviceStatus("Pending").build();
        when(vehicleRepo.save(Mockito.any(Vehicle.class))).thenReturn(vehicle);
        Vehicle result = vehicleService.addVehicle(vehicle);
        assertEquals(vehicle, result);
    }

    @Test
    public void updateVehicleTest() {

        long vehicleId = 0L;
        Vehicle vehicle = Vehicle.builder().ownerName("Test").vehicleName("TestVehicle").vehicleDescription("Oil Change").serviceStatus("Done").build();
        when(vehicleRepo.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicleRepo.save(vehicle)).thenReturn(vehicle);
        Vehicle result = vehicleService.updateVehicle(vehicle);
        assertEquals(vehicle, result);
    }

    @Test
    public void updateVehicleErrorTest() {

        Vehicle vehicle = new Vehicle();
        when(vehicleRepo.findById(vehicle.getId())).thenReturn(Optional.empty());
        assertThrows(VehicleUpdateException.class, () -> vehicleService.updateVehicle(vehicle));
    }

    @Test
    public void deleteVehicleByIdTest() {

        long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setServiceStatus("Done");
        when(vehicleRepo.getOne(vehicleId)).thenReturn(vehicle);
        assertDoesNotThrow(() -> vehicleService.deleteVehicleById(vehicleId));
    }

    @Test
    public void deleteVehicleByIdNotDoneStatusTest() {

        long vehicleId = 1;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setServiceStatus("In Progress");
        when(vehicleRepo.getOne(vehicleId)).thenReturn(vehicle);
        assertThrows(VehicleDeleteException.class, () -> vehicleService.deleteVehicleById(vehicleId));
    }

}