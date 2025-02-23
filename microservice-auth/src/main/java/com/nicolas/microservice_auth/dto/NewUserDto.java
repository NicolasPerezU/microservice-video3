package com.nicolas.microservice_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class NewUserDto {


    private String username;
    private String password;
    private String role;
}
