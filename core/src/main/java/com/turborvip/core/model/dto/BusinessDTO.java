package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessDTO {
    long totalJob;
    long numJobSuccess;
    long numJobDone;
    long numJobFail;
    long numJobProcessing;
    long numJobInActive;

    long totalWorkerReqApply;
    long numWorkerApprove;
    long numWorkerReject;
    long numWorkerPending;

    long totalRating;
    long numRatingPending;

    float ratingPoint;
    long numReviewer;

    Date timeExpire;
}
