package com.nicolas.microservice_user.controller;

import com.nicolas.microservice_user.entity.User;
import com.nicolas.microservice_user.model.Car;
import com.nicolas.microservice_user.model.Motorcycle;
import com.nicolas.microservice_user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @CircuitBreaker(name="carsCB", fallbackMethod = "fallbackGetCarsByUserId")
    @GetMapping("/car/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);

    }

    @CircuitBreaker(name="carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/saveCar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable int userId, @RequestBody Car car) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @CircuitBreaker(name="motorcyclesCB", fallbackMethod = "fallbackGetMotorcyclesByUserId")
    @GetMapping("/motorcycle/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Motorcycle> motorcycles = userService.getMotorcycles(userId);
        return ResponseEntity.ok(motorcycles);
    }



    @CircuitBreaker(name="motorcyclesCB", fallbackMethod = "fallbackSaveMotorcycle")
    @PostMapping("/saveMotorcycle/{userId}")
    public ResponseEntity<Motorcycle> saveMotorcycle(@PathVariable int userId, @RequestBody Motorcycle motorcycle) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Motorcycle newMotorcycle = userService.saveMotorcycle(userId, motorcycle);
        return ResponseEntity.ok(newMotorcycle);
    }


    @CircuitBreaker(name="allCB", fallbackMethod = "fallbackGetUserWithCarsAndMotorcycles")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getUserWithCarsAndMotorcycles(@PathVariable int userId) {
        Map<String, Object> result = userService.getUserWithCarsAndMotorcycles(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallbackGetCarsByUserId(@PathVariable int userId,RuntimeException e) {
        return new ResponseEntity("El usuario con el ID " + userId + " tiene los coches en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackSaveCar(@PathVariable int userId, @RequestBody Car car,RuntimeException e) {
        return new ResponseEntity("El usuario con el ID " + userId + " no puede comprar el coche", HttpStatus.OK);
    }

    private ResponseEntity<List<Motorcycle>> fallbackGetMotorcyclesByUserId(@PathVariable int userId,RuntimeException e) {
        return new ResponseEntity("El usuario con el ID " + userId + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Motorcycle> fallbackSaveMotorcycle(@PathVariable int userId, @RequestBody Motorcycle motorcycle,RuntimeException e) {
        return new ResponseEntity("El usuario con el ID " + userId + " no puede comprar la moto", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallbackGetUserWithCarsAndMotorcycles(@PathVariable int userId,RuntimeException e) {
        return new ResponseEntity("El usuario con el ID " + userId + " tiene los coches y motos en el taller", HttpStatus.OK);
    }


}
