package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.turborvip.core.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class JobDTO {
    String name;
    String address;
    ArrayList<String> images;
    String description;
    int quantityWorker;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN_DETAIL, timezone = "UTC")
    Timestamp startDate;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN_DETAIL, timezone = "UTC")
    Timestamp dueDate;

    boolean isVehicle;
    String gender;

    Double lat;
    Double lng;

    Float salary;
}
