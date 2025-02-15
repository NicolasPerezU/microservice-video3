package com.nicolas.microservice_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Motorcycle {

    private String brand;
    private String model;
    private int userId;
}
