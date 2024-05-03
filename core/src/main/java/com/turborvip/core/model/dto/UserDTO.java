package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String birthday;
    private String gender;
    private String phone;
    private String address;
    private String avatar;
    private Double lat;
    private Double lng;

}
