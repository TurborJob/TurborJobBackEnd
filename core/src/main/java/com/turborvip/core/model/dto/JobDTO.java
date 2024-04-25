package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.turborvip.core.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class JobDTO {
    String name;
    String address;
    ArrayList<String> images;
    String description;
    int quantityWorker;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN)
    Date startDate;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN)
    Date dueDate;

    ObjectNode ipAddress;
    boolean isVehicle;
    String gender;

    Double lat;
    Double lng;
}
