package com.arthur.dnevnik.api.Entity;

import com.arthur.dnevnik.api.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    Long id;
    String name;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    Classroom classroom;



}
