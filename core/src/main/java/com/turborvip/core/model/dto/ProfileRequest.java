package com.turborvip.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
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
