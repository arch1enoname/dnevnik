package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.config.UserMapper;
import com.arthur.dnevnik.api.dto.UserDto;
import com.arthur.dnevnik.api.enums.Role;
import com.arthur.dnevnik.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<User> save(UserDto userDto, Role role) {

        if (userRepository.findFirstByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(role)
                .classroom(null)
                .build();

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }

    public ResponseEntity<User> updateUser(UserDto userDto, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(userDto.getEmail());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if(userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = UserMapper.toDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
