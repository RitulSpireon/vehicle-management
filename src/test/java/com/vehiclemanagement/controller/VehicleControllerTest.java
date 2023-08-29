package com.vehiclemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.service.VehicleService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle vehicle;

    @BeforeEach
    public void init() {
        vehicle = Vehicle.builder().ownerName("Test").vehicleName("TestVehicle").vehicleDescription("Oil Change").serviceStatus("Pending").build();
    }


    @Test
    public void addVehicleTest() throws Exception {
        // Mock the behavior of the vehicleService.addVehicle method
        given(vehicleService.addVehicle(any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.ownerName", CoreMatchers.is(vehicle.getOwnerName())))
                .andExpect(jsonPath("$.vehicleName", CoreMatchers.is(vehicle.getVehicleName())))
                .andExpect(jsonPath("$.vehicleDescription", CoreMatchers.is(vehicle.getVehicleDescription())))
                .andExpect(jsonPath("$.serviceStatus", CoreMatchers.is(vehicle.getServiceStatus())));
    }

    @Test
    public void getVehiclesTest() throws Exception {
        List<Vehicle> vehicles = Arrays.asList(
                Vehicle.builder().ownerName("Test1").vehicleName("TestVehicle1").vehicleDescription("Oil Change").serviceStatus("Pending").build(),
        Vehicle.builder().ownerName("Test2").vehicleName("TestVehicle2").vehicleDescription("Break Change").serviceStatus("Pending").build()
        );

        when(vehicleService.getVehicles()).thenReturn(vehicles);

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2)); // Check the size of the JSON array
    }

    @Test
    public void getVehicleById() throws Exception {
        long vehicleId = 1L;
        when(vehicleService.getVehicleById(vehicleId)).thenReturn(vehicle);

        ResultActions response = mockMvc.perform(get("/vehicles/"+vehicleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerName", CoreMatchers.is(vehicle.getOwnerName())))
                .andExpect(jsonPath("$.vehicleName", CoreMatchers.is(vehicle.getVehicleName())))
                .andExpect(jsonPath("$.vehicleDescription", CoreMatchers.is(vehicle.getVehicleDescription())))
                .andExpect(jsonPath("$.serviceStatus", CoreMatchers.is(vehicle.getServiceStatus())));
    }


    @Test
    public void updateVehicleTest() throws Exception {
        // Create a mock vehicle for testing
        Vehicle updatedVehicle =  Vehicle.builder().ownerName("Test").vehicleName("TestVehicle").vehicleDescription("Oil Change").serviceStatus("Done").build();

        when(vehicleService.updateVehicle(any(Vehicle.class))).thenReturn(updatedVehicle);

        mockMvc.perform(put("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.serviceStatus").value("Done"));
    }

    @Test
    public void deleteVehicleByIdTest() throws Exception {
        long vehicleId = 1L;

        mockMvc.perform(delete("/vehicles/{vehicleId}", vehicleId))
                .andExpect(status().isOk());
    }
}
