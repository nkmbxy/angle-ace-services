package com.project.angleace.service;

import com.project.angleace.entity.User;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.SignupRequest;
import com.project.angleace.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User signup(SignupRequest request) {
        Optional<User> userRepo = userRepository.findByEmail(request.getEmail());
        if (userRepo.isPresent()) {
            logger.info("dupicate email in database");
            throw new Exception();
        }
        User user = new User()
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder().encode(request.getPassword()))
                .setAddress(request.getAddress())
                .setFirstName(request.getFirstname())
                .setLastName(request.getLastname())
                .setPhone(request.getPhone());

        logger.info("request: {}", request);
        userRepository.save(user);
        logger.info("result: {}", user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.get().getEmail())
                        .password(user.get().getPassword())
                        .build();
        return userDetails;
    }

}
