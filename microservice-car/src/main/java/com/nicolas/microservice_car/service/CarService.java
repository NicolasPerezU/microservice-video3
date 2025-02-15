package com.nicolas.microservice_car.service;

import com.nicolas.microservice_car.entity.Car;
import com.nicolas.microservice_car.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListResourceBundle;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;


    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getById(int id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car){
        return carRepository.save(car);
    }

    public List<Car> getByUserId(int userId){
        return carRepository.findByUserId(userId);
    }



}
