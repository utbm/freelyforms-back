package com.utbm.da50.freelyform.dto.user;


import com.utbm.da50.freelyform.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private UserRole role;
}