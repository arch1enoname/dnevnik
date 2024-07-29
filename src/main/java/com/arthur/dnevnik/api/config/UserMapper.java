package com.arthur.dnevnik.api.config;

import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.dto.UserDto;

public class UserMapper {
    public static User fromDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        if (user.getClassroom() != null) {
            userDto.setClassroomId(user.getClassroom().getId());
            userDto.setClassroomName(user.getClassroom().getName());
        }
        return userDto;
    }
}
