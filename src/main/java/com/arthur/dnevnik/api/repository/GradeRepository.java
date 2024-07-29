package com.arthur.dnevnik.api.repository;

import com.arthur.dnevnik.api.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
