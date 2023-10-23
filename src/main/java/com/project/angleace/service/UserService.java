package com.project.angleace.service;

import com.project.angleace.entity.User;
import com.project.angleace.model.request.SignupRequest;
import com.project.angleace.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private UserRepository userRepository;

    public User signup(SignupRequest request) {


        User user = new User()
                .setEmail(request.getUsername())
                .setPassword(request.getPassword());

        logger.info("request: {}", request);
        userRepository.save(user);
        logger.info("result: {}", user);
        return user;
    }

}
