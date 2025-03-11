package com.cenfotec.p3.neuralforge_api.controller;

import com.cenfotec.p3.neuralforge_api.model.resource.AuthenticationResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserResource;
import com.cenfotec.p3.neuralforge_api.service.AuthenticationService;
import com.cenfotec.p3.neuralforge_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResource> loginUser(@RequestBody UserResource user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationService.authenticate(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResource> registerUser(@Valid @RequestBody UserResource user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }


}
