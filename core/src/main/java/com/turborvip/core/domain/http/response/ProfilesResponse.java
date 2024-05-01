package com.turborvip.core.domain.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilesResponse {
    List<?> users;
    long total;
}