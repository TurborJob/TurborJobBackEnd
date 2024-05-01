package com.turborvip.core.domain.http.response;


import com.turborvip.core.model.dto.Profile;
import com.turborvip.core.model.dto.ProfileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJobInfo {
   private ProfileRequest user;
   private String jobName;
}
