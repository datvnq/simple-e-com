package com.example.simpleecom.service;

import com.example.simpleecom.dto.RegisterRequest;
import com.example.simpleecom.dto.UserDto;
import com.example.simpleecom.entity.User;
import com.example.simpleecom.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setRoles("USER");
        user.setCreated(Instant.now());

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto getUserByUsername(String username) {
        User tempUser =  userRepository.findByUsername(username).orElseThrow();
        return userEntitytoDto(tempUser);
    }

    public UserDto userEntitytoDto(User user) {
        UserDto tempUserDto = new UserDto();
        tempUserDto.setId(user.getId());
        tempUserDto.setUsername(user.getUsername());
        tempUserDto.setEmail(user.getEmail());
        tempUserDto.setRoles(user.getRoles());

        return tempUserDto;
    }
}
