package com.nicolas.microservice_user.feignclient;

import com.nicolas.microservice_user.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-car", url = "http://localhost:8002")

public interface CarClient {


    @PostMapping("/car")
    Car createCar(@RequestBody Car car);

    @GetMapping("/car/user/{userId}")
    List<Car> getByUserId(@PathVariable int userId);
}
