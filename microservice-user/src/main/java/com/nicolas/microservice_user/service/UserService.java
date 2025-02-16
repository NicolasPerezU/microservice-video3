package com.nicolas.microservice_user.service;

import com.nicolas.microservice_user.entity.User;
import com.nicolas.microservice_user.feignclient.CarClient;
import com.nicolas.microservice_user.feignclient.MotorcycleClient;
import com.nicolas.microservice_user.model.Car;
import com.nicolas.microservice_user.model.Motorcycle;
import com.nicolas.microservice_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarClient carClient;

    @Autowired
    private MotorcycleClient motorcycleClient;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCars(int userId) {
        List<Car> cars = restTemplate.getForObject("http://microservice-car/car/user/" + userId, List.class);

        return cars;
    }

    public List<Motorcycle> getMotorcycles(int userId) {
        List<Motorcycle> motorcycles = restTemplate.getForObject("http://microservice-motorcycle/motorcycle/user/" + userId, List.class);

        return motorcycles;
    }


    public Car saveCar(int userId,Car car) {
        car.setUserId(userId);
        Car newCar = carClient.createCar(car);
        return newCar;
    }

    public Motorcycle saveMotorcycle(int userId,Motorcycle motorcycle) {
        motorcycle.setUserId(userId);
        Motorcycle newMotorcycle = motorcycleClient.createMotorcycle(motorcycle);
        return newMotorcycle;
    }

    public Map<String, Object> getUserWithCarsAndMotorcycles(int userId) {
        Map<String,Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }
        result.put("user",user);
        List<Car> cars = carClient.getByUserId(userId);
        if (cars == null || cars.isEmpty()) {
            return null;
        }
        List<Motorcycle> motorcycles = motorcycleClient.getMotorcyclesByUserId(userId);
        if (motorcycles == null || motorcycles.isEmpty()) {
            return null;
        }
        result.put("cars",cars);
        result.put("motorcycles",motorcycles);
        return result;
    }



}
