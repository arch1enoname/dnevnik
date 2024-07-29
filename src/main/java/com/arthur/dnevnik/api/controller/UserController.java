package com.arthur.dnevnik.api.controller;

import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.dto.UserDto;
import com.arthur.dnevnik.api.enums.Role;
import com.arthur.dnevnik.api.service.UserService;
import com.arthur.dnevnik.api.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "dnevnikapi")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        return userService.save(userDto, Role.STUDENT);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @PostMapping("/createTeacher")
    public ResponseEntity<User> createTeacher(@RequestBody UserDto userDto) {
        return userService.save(userDto, Role.TEACHER);
    }

}
