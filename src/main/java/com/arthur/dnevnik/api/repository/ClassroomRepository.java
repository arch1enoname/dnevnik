package com.arthur.dnevnik.api.repository;

import com.arthur.dnevnik.api.Entity.Classroom;
import com.arthur.dnevnik.api.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByName(String name);
    Optional<Classroom> findClassroomById(Long id);
}
