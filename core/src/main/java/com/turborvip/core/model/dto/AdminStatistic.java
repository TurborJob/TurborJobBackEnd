package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminStatistic {
    long totalContacts;
    long numContactReplied;

    long totalAccounts;
    long numAccountWorker;
    long numAccountBusiness;
    long numAccountAdmin;

    long totalSessions;
    long totalDevices;
}
