package com.project.angleace.controller;

import com.project.angleace.entity.Product;
import com.project.angleace.entity.User;
import com.project.angleace.model.request.SignupRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.ProductService;
import com.project.angleace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<Response<User>> signup(
            @RequestBody SignupRequest signupRequest
    ) {
        User user = userService.signup(signupRequest);

        return new Response<User>("200",user).response();
    }
}

