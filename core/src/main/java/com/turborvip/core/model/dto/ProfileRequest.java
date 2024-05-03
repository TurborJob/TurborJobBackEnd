package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileRequest {
    private long id;
    private String fullName;
    private String email;
    private String birthday;
    private String gender;
    private String phone;
    private String address;
    private String avatar;
    private float rating;
    private long countRate;
    private String noteMessage;
}
