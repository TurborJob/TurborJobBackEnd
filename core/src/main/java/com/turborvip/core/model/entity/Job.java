package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.turborvip.core.model.entity.base.AbstractBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "jobs",schema = "job")
public class Job extends AbstractBase {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "images", columnDefinition = "TEXT[]")
    private ArrayList<String> images;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity_worker_current")
    private int quantityWorkerCurrent = 0;

    @Column(name = "quantity_worker_total")
    private int quantityWorkerTotal = 1;

    @NotEmpty(message = "Start date must not be empty")
    @Column(name = "start_date")
    private Timestamp startDate;

    @NotEmpty(message = "Due date must not be empty")
    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "is_vehicle")
    private boolean isVehicle;

    @Column(name = "gender")
    private String gender;

    @Column(name = "viewer_num")
    private long viewer_num = 0;

    @Column(name = "status")
    private String status = "inactive";
    // inactive, active, success, processing, done, fail.


    public Job(String name, String address, ArrayList<String> images, String description, int quantityWorkerTotal, Timestamp startDate, Timestamp dueDate, ObjectNode ipAddress, boolean isVehicle, String gender, Double lat,Double lng) {
        this.name = name;
        this.address = address;
        this.images = images;
        this.description = description;
        this.quantityWorkerTotal = quantityWorkerTotal;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.lat = lat;
        this.lng = lng;
        this.isVehicle = isVehicle;
        this.gender = gender;
    }
}
