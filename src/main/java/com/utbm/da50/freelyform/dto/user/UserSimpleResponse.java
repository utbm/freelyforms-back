package com.utbm.da50.freelyform.dto.user;

import com.utbm.da50.freelyform.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class UserSimpleResponse {


    String id;
    String firstName;
    String lastName;
    String email;
    HashSet<UserRole> role;

}
