package com.nicolas.microservice_auth.controller;

import com.nicolas.microservice_auth.dto.AuthUserDTO;
import com.nicolas.microservice_auth.dto.TokenDTO;
import com.nicolas.microservice_auth.entity.AuthUser;
import com.nicolas.microservice_auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;


    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthUserDTO authUserDTO){
        TokenDTO token = authUserService.login(authUserDTO);
        if(token == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(token);
    }


    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validateToken(@RequestBody String token){
        TokenDTO tokenDTO = authUserService.validateToken(token);
        if(tokenDTO == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<AuthUser> createUser(@RequestBody AuthUserDTO authUserDTO){
        AuthUser user = authUserService.saveUser(authUserDTO);
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }




}
