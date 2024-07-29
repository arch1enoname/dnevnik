package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.Classroom;
import com.arthur.dnevnik.api.dto.ClassroomDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    Classroom getClassroomById(Long id);
    Classroom getClassroomByName(String name);
    Classroom createClassroom(ClassroomDto classroomDto);
    Classroom updateClassroom(Long id, String name);
    String deleteClassroom(Long id);
    List<Classroom> getAllClassrooms();

}
