package com.nicolas.microservice_auth.service;

import com.nicolas.microservice_auth.dto.AuthUserDTO;
import com.nicolas.microservice_auth.dto.NewUserDto;
import com.nicolas.microservice_auth.dto.RequestDTO;
import com.nicolas.microservice_auth.dto.TokenDTO;
import com.nicolas.microservice_auth.entity.AuthUser;
import com.nicolas.microservice_auth.repository.AuthUserRepository;
import com.nicolas.microservice_auth.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;



    public AuthUser saveUser(NewUserDto DTO){
        Optional<AuthUser> user = authUserRepository.findByUsername(DTO.getUsername());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(DTO.getPassword());
        AuthUser newUser = new AuthUser();
        newUser.setUsername(DTO.getUsername());
        newUser.setPassword(password);
        newUser.setRole(DTO.getRole());
        return authUserRepository.save(newUser);
    }



    public TokenDTO login(AuthUserDTO authUserDTO){
        Optional<AuthUser> user = authUserRepository.findByUsername(authUserDTO.getUsername());
        if(!user.isPresent()){
            return null;
        }
        if (passwordEncoder.matches(authUserDTO.getPassword(), user.get().getPassword())){
            return new TokenDTO(jwtProvider.createToken(user.get()));
        }
        return null;
    }


    public TokenDTO validateToken(String token, RequestDTO dto){
       if (!jwtProvider.validateToken(token,dto)){
           return null;
       }
       String username = jwtProvider.getUsernameFromToken(token);
       if (!authUserRepository.findByUsername(username).isPresent()){
           return null;
       }
         return new TokenDTO(token);
    }





}
