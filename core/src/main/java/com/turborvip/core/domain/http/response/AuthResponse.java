package com.turborvip.core.domain.http.response;

import com.turborvip.core.model.dto.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private  String token = null;
    private  String refreshToken = null;
    private Profile profile = null;
}
