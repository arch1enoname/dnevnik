package com.arthur.dnevnik.api.dto;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String name;
    String email;
    String password;
    Long classroomId;
    String classroomName;

}
