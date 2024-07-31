package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.Grade;
import com.arthur.dnevnik.api.Entity.User;
import com.arthur.dnevnik.api.dto.GradeDto;
import com.arthur.dnevnik.api.enums.Role;
import com.arthur.dnevnik.api.repository.ClassroomRepository;
import com.arthur.dnevnik.api.repository.GradeRepository;
import com.arthur.dnevnik.api.repository.SubjectRepository;
import com.arthur.dnevnik.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository,
                            UserRepository userRepository,
                            SubjectRepository subjectRepository,
                            ClassroomRepository classroomRepository) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.classroomRepository = classroomRepository;
    }

    public void rateStudent(GradeDto gradeDto) {

        if (userRepository.findById(gradeDto.getStudentId()).isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        if (subjectRepository.findById(gradeDto.getSubjectId()).isEmpty()) {
            throw new RuntimeException("Subject not found");
        }
        if(userRepository.findById(gradeDto.getTeacherId()).isEmpty()) {
            throw new RuntimeException("Teacher not found");
        }

        Grade grade = Grade.builder()
                .value(gradeDto.getValue())
                .importance(gradeDto.getImportance())
                .dateOfRate(gradeDto.getDateOfRate())
                .student(userRepository.findById(gradeDto.getStudentId()).get())
                .teacher(userRepository.findById(gradeDto.getTeacherId()).get())
                .subject(subjectRepository.findById(gradeDto.getSubjectId()).get())
                .build();
        gradeRepository.save(grade);

    }

    public List<GradeDto> getGradesByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        List<Grade> grades = gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
        List<GradeDto> gradeDtos = new ArrayList<>();

        for (Grade grade : grades) {
            GradeDto gradeDto = new GradeDto();

            gradeDto.setStudentId(studentId);
            gradeDto.setSubjectId(subjectId);
            gradeDto.setDateOfRate(grade.getDateOfRate());
            gradeDto.setValue(grade.getValue());
            gradeDto.setImportance(grade.getImportance());
            gradeDto.setTeacherId(grade.getTeacher().getId());
            gradeDtos.add(gradeDto);
        }
        return gradeDtos.stream().sorted((g1, g2) -> g1.getDateOfRate().compareTo(g2.getDateOfRate())).toList();
    }

    public Double getWeightedAverageGradeByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        List<Grade> grades = gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
        Integer sumOfGrades = 0;
        Integer gradesCount = 0;
        for (Grade grade : grades) {
            sumOfGrades += grade.getValue()*grade.getImportance();
            gradesCount += grade.getImportance();
        }
        Double weightedAverage = sumOfGrades.doubleValue() / gradesCount.doubleValue();
        return Math.round(weightedAverage * 10.0) / 10.0;
    }

    public Double getAverageGradeByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        List<Grade> grades = gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
        Integer sumOfGrades = 0;
        for (Grade grade : grades) {
            sumOfGrades += grade.getValue();
        }
        Double average = sumOfGrades.doubleValue() / grades.size();
        return Math.round(average * 10.0) / 10.0;
    }

    public Double getAverageGradeByClassroomIdAndSubjectId(Long classroomId, Long subjectId) {
        if (classroomRepository.findClassroomById(classroomId).isEmpty()) {
            throw new RuntimeException("Classroom not found");
        }
        if (subjectRepository.findById(subjectId).isEmpty()) {
            throw new RuntimeException("Subject not found");
        }

        List<User> students = classroomRepository.findClassroomById(classroomId).get().getUsers()
                .stream().filter(student -> student.getRole() == Role.STUDENT).toList();
        Double averageGrade = 0.0;
        Integer zeroCount = 0;
        for (User user : students) {
            if (getAverageGradeByStudentIdAndSubjectId(user.getId(), subjectId) == 0) {
                zeroCount++;
            } else {
                averageGrade += getAverageGradeByStudentIdAndSubjectId(user.getId(), subjectId);
            }
        }
        return averageGrade / (students.size() - zeroCount);
    }

    public Double getWeightedAverageGradeByClassroomIdAndSubjectId(Long classroomId, Long subjectId) {
        if (classroomRepository.findClassroomById(classroomId).isEmpty()) {
            throw new RuntimeException("Classroom not found");
        }
        if (subjectRepository.findById(subjectId).isEmpty()) {
            throw new RuntimeException("Subject not found");
        }

        List<User> students = classroomRepository.findClassroomById(classroomId).get().getUsers()
                .stream().filter(student -> student.getRole() == Role.STUDENT).toList();
        Double averageGrade = 0.0;
        Integer zeroCount = 0;
        for (User user : students) {
            if (getAverageGradeByStudentIdAndSubjectId(user.getId(), subjectId) == 0) {
                zeroCount++;
            } else {
                averageGrade += getWeightedAverageGradeByStudentIdAndSubjectId(user.getId(), subjectId);
            }
        }
        return averageGrade / (students.size() - zeroCount);
    }
}
