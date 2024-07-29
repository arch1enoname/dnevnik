package com.arthur.dnevnik.api.controller;

import com.arthur.dnevnik.api.Entity.Classroom;
import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.dto.SubjectDto;
import com.arthur.dnevnik.api.dto.UserDto;
import com.arthur.dnevnik.api.enums.Role;
import com.arthur.dnevnik.api.service.ClassroomService;
import com.arthur.dnevnik.api.service.ClassroomServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
@SecurityRequirement(name = "dnevnikapi")
public class ClassroomController {

    private final ClassroomServiceImpl classroomService;
    @Autowired
    public ClassroomController(ClassroomServiceImpl classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping("/addUserToClassroom/{classroomId}/{userId}")
    public ResponseEntity<Classroom> addUserToClassroom(@PathVariable Long classroomId, @PathVariable Long userId) {
        classroomService.addUserToClassroom(classroomId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Classroom> getClassroomByName(@PathVariable String name) {
        return new ResponseEntity<>(classroomService.getClassroomByName(name), HttpStatus.OK);
    }

    @GetMapping("/{classroomId}/users")
    public ResponseEntity<List<UserDto>> getUsersByClassroom(@PathVariable Long classroomId) {
        return new ResponseEntity<List<UserDto>>(classroomService.getUsersFromClassroom(classroomId), HttpStatus.OK);
    }

    @GetMapping("/{classroomId}/users/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Long classroomId, @PathVariable String role) {
        return new ResponseEntity<List<UserDto>>(classroomService.getUsersByRole(classroomId, Role.valueOf(role.toUpperCase())), HttpStatus.OK);
    }

    @PostMapping("/addSubjectToClassroom/{classroomId}/{subjectId}")
    public ResponseEntity<Classroom> addSubjectToClassroom(@PathVariable Long classroomId, @PathVariable Long subjectId) {
        classroomService.addSubjectToClassroom(classroomId, subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{classroomId}/subjects")
    public ResponseEntity<List<SubjectDto>> getSubjectsByClassroom(@PathVariable Long classroomId) {
        return new ResponseEntity<>(classroomService.getSubjectsFromClassroom(classroomId), HttpStatus.OK);
    }
}
