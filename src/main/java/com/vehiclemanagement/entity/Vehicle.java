package com.vehiclemanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Vehicle implements Serializable {
    @Id
    private long id;
    private String ownerName;
    private String vehicleName;
    private String vehicleDescription;
    private String serviceStatus;

}
