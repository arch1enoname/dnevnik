package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.dto.UserDto;
import com.arthur.dnevnik.api.enums.Role;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    ResponseEntity<User> save(UserDto userDto, Role role);
}
