package com.nicolas.microservice_user.feignclient;

import com.nicolas.microservice_user.model.Motorcycle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-motorcycle")
public interface MotorcycleClient {

    @PostMapping("/motorcycle")
    Motorcycle createMotorcycle(@RequestBody Motorcycle motorcycle);

    @GetMapping("/motorcycle/user/{userId}")
    List<Motorcycle> getMotorcyclesByUserId(@PathVariable int userId);
}
