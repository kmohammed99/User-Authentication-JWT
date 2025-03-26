package com.esc.security.controller;

import com.esc.security.dto.UserModelDTO;
import com.esc.security.jwt.JWTUtil;
import com.esc.security.model.UserModel;
import com.esc.security.model.UserRegisterRequest;
import com.esc.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("esc/user")
@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;

        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<UserModel> addUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        userService.insertCustomer(userRegisterRequest);

        UUID id = userService.userByEmail(userRegisterRequest.email()).getId();
        String jwtToken = jwtUtil.issueToken(userRegisterRequest.email(), userRegisterRequest.role());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @GetMapping("/allUsers")
    public List<UserModelDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/byEmail/{email}")
    public UserModelDTO getByEmail(@PathVariable String email) {
        return userService.userByEmailMapper(email);
    }
}
