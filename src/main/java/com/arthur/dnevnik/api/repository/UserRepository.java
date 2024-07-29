package com.arthur.dnevnik.api.repository;

import com.arthur.dnevnik.api.Entity.Classroom;
import com.arthur.dnevnik.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
    Optional<User> findById(Long id);
}
