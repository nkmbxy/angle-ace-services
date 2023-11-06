package com.project.angleace.controller;

import com.project.angleace.auth.Jwt;
import com.project.angleace.entity.User;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.LoginRequest;
import com.project.angleace.model.request.SignupRequest;
import com.project.angleace.model.response.LoginSignupResponse;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationManager authenticationManager;
    private final Jwt jwtUtil;
    @Autowired
    private UserService userService;

    public UserController(AuthenticationManager authenticationManager, Jwt jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/signup")
    public ResponseEntity<Response<LoginSignupResponse>> signup(
            @RequestBody SignupRequest signupRequest
    ) {
        User user = userService.signup(signupRequest);
        String token = jwtUtil.createToken(user);
        LoginSignupResponse loginSignupResponse = new LoginSignupResponse().setEmail(user.getEmail()).setToken(token);

        return new Response<LoginSignupResponse>("200", loginSignupResponse).response();

    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginSignupResponse>> login(
            @RequestBody LoginRequest loginRequest
    ) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            String email = authentication.getName();
            User user = new User().setEmail(email);
            String token = jwtUtil.createToken(user);
            LoginSignupResponse loginRes = new LoginSignupResponse().setEmail(email).setToken(token);

            return new Response<LoginSignupResponse>("200", loginRes).response();
        } catch (BadCredentialsException e) {
            logger.info("Invalid username or password error: {}", e.getMessage());
            throw new Exception();
        } catch (Exception e) {
            logger.info("exception error: {}", e.getMessage());
            throw new Exception();
        }

    }
}
