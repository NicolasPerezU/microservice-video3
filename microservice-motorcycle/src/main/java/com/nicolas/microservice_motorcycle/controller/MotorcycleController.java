package com.nicolas.microservice_motorcycle.controller;

import com.nicolas.microservice_motorcycle.entity.Motorcycle;
import com.nicolas.microservice_motorcycle.service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorcycle")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;


    @GetMapping
    public ResponseEntity<List<Motorcycle>> getAllMotorcycles() {
        List<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();
        if (motorcycles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motorcycles);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycleById(@PathVariable int id) {
        Motorcycle motorcycle = motorcycleService.getMotorcycleById(id);
        if (motorcycle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motorcycle);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable int userId) {
        List<Motorcycle> motorcycles = motorcycleService.getMotorcycleByUserId(userId);
        if (motorcycles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motorcycles);
    }

    @PostMapping
    public ResponseEntity<Motorcycle> createMotorcycle(@RequestBody Motorcycle motorcycle) {
        Motorcycle newMotorcycle = motorcycleService.saveMotorcycle(motorcycle);
        return ResponseEntity.ok(newMotorcycle);
    }

}
