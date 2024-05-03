package com.turborvip.core.domain.http.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turborvip.core.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class JobResponse {
    long id;
    String name;
    String address;
    ArrayList<String> images;
    String description;
    int quantityWorkerTotal;
    int quantityWorkerCurrent;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN_DETAIL)
    Timestamp startDate;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN_DETAIL)
    Timestamp dueDate;

    boolean isVehicle;
    String gender;
    Long viewerNum;

    Float salary;

    String status;
}
