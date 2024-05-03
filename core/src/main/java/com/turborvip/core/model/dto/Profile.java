package com.turborvip.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Profile {
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

    public Profile(String fullName, String email, String birthday, String gender, String phone, String address, String avatar, float rating, long countRate) {
        this.fullName = fullName;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.rating = rating;
        this.countRate = countRate;
    }
}
