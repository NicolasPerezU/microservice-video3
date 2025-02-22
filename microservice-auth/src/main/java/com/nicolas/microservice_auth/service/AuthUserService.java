package com.nicolas.microservice_auth.service;

import com.nicolas.microservice_auth.dto.AuthUserDTO;
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



    public AuthUser saveUser(AuthUserDTO authUserDTO){
        Optional<AuthUser> user = authUserRepository.findByUsername(authUserDTO.getUsername());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(authUserDTO.getPassword());
        AuthUser newUser = new AuthUser();
        newUser.setUsername(authUserDTO.getUsername());
        newUser.setPassword(password);
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


    public TokenDTO validateToken(String token){
       if (!jwtProvider.validateToken(token)){
           return null;
       }
       String username = jwtProvider.getUsernameFromToken(token);
       if (!authUserRepository.findByUsername(username).isPresent()){
           return null;
       }
         return new TokenDTO(token);
    }





}
