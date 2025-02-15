package com.nicolas.microservice_car.controller;

import com.nicolas.microservice_car.entity.Car;
import com.nicolas.microservice_car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAll();
        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        Car car = carService.getById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable int userId) {
        List<Car> cars = carService.getByUserId(userId);
        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car newCar = carService.save(car);
        if (newCar == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newCar);
    }

}
