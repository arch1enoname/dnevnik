package com.arthur.dnevnik.api.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "grades")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer value;
    Integer importance;
    @ManyToOne
    User teacher;
    @ManyToOne
    User student;
    @ManyToOne
    Subject subject;
    Date dateOfRate;

}
