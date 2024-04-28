package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"coordinates"})
@Table(name = "jobs",schema = "job")
public class Job extends AbstractBase {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "salary")
    private float salary;

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

    @Column(name = "coordinates",columnDefinition = "Geometry(Point, 4326)")
    @JsonIgnore
    private Point coordinates;

    @Column(name = "is_vehicle")
    private boolean isVehicle;

    @Column(name = "gender")
    private String gender;

    @Column(name = "viewer_num")
    private long viewerNum = 0;

    @Column(name = "status")
    private String status = "inactive";
    // inactive, processing, success, done, fail.


    public Job(String name, String address, ArrayList<String> images, String description, int quantityWorkerTotal, Timestamp startDate, Timestamp dueDate, boolean isVehicle, String gender, Double lat,Double lng ,float salary) {
        this.name = name;
        this.address = address;
        this.images = images;
        this.description = description;
        this.quantityWorkerTotal = quantityWorkerTotal;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.isVehicle = isVehicle;
        this.gender = gender;
        this.salary = salary;

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.coordinates = geometryFactory.createPoint(new Coordinate(lat,lng));
    }
}
