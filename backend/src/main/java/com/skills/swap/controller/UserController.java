package com.skills.swap.controller;

import com.skills.swap.dto.UserProfileDTO;
import com.skills.swap.model.User;
import com.skills.swap.repository.UserRepository;
import com.skills.swap.service.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private DataMapper dataMapper;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(dataMapper.toUserProfileDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}