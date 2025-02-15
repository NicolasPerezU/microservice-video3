package com.nicolas.microservice_motorcycle.repository;

import com.nicolas.microservice_motorcycle.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {

    List<Motorcycle> findByUserId(Integer userId);

}
