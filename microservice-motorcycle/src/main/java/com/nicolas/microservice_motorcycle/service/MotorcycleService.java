package com.nicolas.microservice_motorcycle.service;

import com.nicolas.microservice_motorcycle.entity.Motorcycle;
import com.nicolas.microservice_motorcycle.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleRepository.findAll();
    }

    public Motorcycle getMotorcycleById(int id) {
        return motorcycleRepository.findById(id).orElse(null);
    }

    public List<Motorcycle> getMotorcycleByUserId(int userId) {
        return motorcycleRepository.findByUserId(userId);
    }

    public Motorcycle saveMotorcycle(Motorcycle motorcycle) {
        return motorcycleRepository.save(motorcycle);
    }




}
