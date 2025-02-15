package com.nicolas.microservice_car.repository;

import com.nicolas.microservice_car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByUserId(int userId);
}
