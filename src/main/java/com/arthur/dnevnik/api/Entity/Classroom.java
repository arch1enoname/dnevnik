package com.arthur.dnevnik.api.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "classrooms")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    Long id;
    String name;
    @OneToMany(mappedBy = "classroom")
    List<User> users  = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "classroom_subject",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    List<Subject> subjects = new ArrayList<>();

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
