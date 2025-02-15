package com.nicolas.microservice_user.controller;

import com.nicolas.microservice_user.entity.User;
import com.nicolas.microservice_user.model.Car;
import com.nicolas.microservice_user.model.Motorcycle;
import com.nicolas.microservice_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/car/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);

    }

    @GetMapping("/motorcycle/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Motorcycle> motorcycles = userService.getMotorcycles(userId);
        return ResponseEntity.ok(motorcycles);
    }

    @PostMapping("/saveCar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable int userId, @RequestBody Car car) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @PostMapping("/saveMotorcycle/{userId}")
    public ResponseEntity<Motorcycle> saveMotorcycle(@PathVariable int userId, @RequestBody Motorcycle motorcycle) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Motorcycle newMotorcycle = userService.saveMotorcycle(userId, motorcycle);
        return ResponseEntity.ok(newMotorcycle);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getUserWithCarsAndMotorcycles(@PathVariable int userId) {
        Map<String, Object> result = userService.getUserWithCarsAndMotorcycles(userId);
        return ResponseEntity.ok(result);
    }

}
