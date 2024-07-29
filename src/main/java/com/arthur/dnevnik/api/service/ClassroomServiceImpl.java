package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.Classroom;
import com.arthur.dnevnik.api.Entity.Subject;
import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.config.UserMapper;
import com.arthur.dnevnik.api.dto.ClassroomDto;
import com.arthur.dnevnik.api.dto.SubjectDto;
import com.arthur.dnevnik.api.dto.UserDto;
import com.arthur.dnevnik.api.enums.Role;
import com.arthur.dnevnik.api.repository.ClassroomRepository;
import com.arthur.dnevnik.api.repository.SubjectRepository;
import com.arthur.dnevnik.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository classroomRepository,
                                UserServiceImpl userService,
                                UserRepository userRepository,
                                SubjectRepository subjectRepository) {
        this.classroomRepository = classroomRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Classroom getClassroomById(Long id) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(id);
        if (classroomOptional.isPresent()) {
            return classroomOptional.get();
        } else {
            throw new RuntimeException("Classroom not found");
        }
    }

    @Override
    public Classroom getClassroomByName(String name) {
        Optional<Classroom> classroomOptional = classroomRepository.findByName(name);
        if (classroomOptional.isPresent()) {
            return classroomOptional.get();
        } else {
            throw new RuntimeException("Classroom not found");
        }
    }

    @Override
    public Classroom createClassroom(ClassroomDto classroomDto) {
        Classroom classroom = Classroom.builder()
                .name(classroomDto.getName())
                .build();

        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom updateClassroom(Long id, String name) {
        return null;
    }

    @Override
    public String deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
        return "Success";
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public void addUserToClassroom(Long classroomId, Long userId) {
        Classroom classroom = getClassroomById(classroomId);
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            classroom.getUsers().add(user);
            classroomRepository.save(classroom);

            user.setClassroom(classroom);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<UserDto> getUsersFromClassroom(Long classroomId) {
        Classroom classroom = getClassroomById(classroomId);
        List<User> users = classroom.getUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toDto(user));
        }
        return userDtos;
    }

    public List<UserDto> getUsersByRole(Long classroomId, Role role) {
        Classroom classroom = getClassroomById(classroomId);
        List<User> users = classroom.getUsers().stream().filter(u -> u.getRole() == role).toList();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toDto(user));
        }
        return userDtos;
    }

    public void addSubjectToClassroom(Long classroomId, Long subjectId) {
        Classroom classroom = getClassroomById(classroomId);
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isPresent()) {
            classroom.getSubjects().add(subject.get());
            subject.get().getClassrooms().add(classroom);
            subjectRepository.save(subject.get());
            classroomRepository.save(classroom);
        } else {
            throw new RuntimeException("Subject not found");
        }

    }

    public List<SubjectDto> getSubjectsFromClassroom(Long classroomId) {
        Classroom classroom = getClassroomById(classroomId);
        List<Subject> subjects = classroom.getSubjects();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDto subjectDto = new SubjectDto(subject.getName());
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }
}
