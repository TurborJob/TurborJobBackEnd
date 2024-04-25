package com.turborvip.core.domain.http.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    private String fullName;
    private String email;
    private String birthday;
    private String gender;
    private String phone;
    private String address;
    private String avatar;
}
