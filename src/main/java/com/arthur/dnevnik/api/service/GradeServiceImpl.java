package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.Grade;
import com.arthur.dnevnik.api.dto.GradeDto;
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

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository,
                            UserRepository userRepository,
                            SubjectRepository subjectRepository) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public void rateStudent(GradeDto gradeDto) {

        if(userRepository.findById(gradeDto.getStudentId()).isPresent()
                && subjectRepository.findById(gradeDto.getSubjectId()).isPresent()
                && userRepository.findById(gradeDto.getTeacherId()).isPresent()
        ) {
            Grade grade = Grade.builder()
                    .value(gradeDto.getValue())
                    .importance(gradeDto.getImportance())
                    .student(userRepository.findById(gradeDto.getStudentId()).get())
                    .teacher(userRepository.findById(gradeDto.getTeacherId()).get())
                    .subject(subjectRepository.findById(gradeDto.getSubjectId()).get())
                    .build();
            gradeRepository.save(grade);
        } else {
            throw new RuntimeException("404 error");
        }
    }

    public List<GradeDto> getGradesByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        List<Grade> grades = gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
        List<GradeDto> gradeDtos = new ArrayList<>();

        for (Grade grade : grades) {
            GradeDto gradeDto = new GradeDto();

            gradeDto.setStudentId(studentId);
            gradeDto.setSubjectId(subjectId);
            gradeDto.setValue(grade.getValue());
            gradeDto.setImportance(grade.getImportance());
            gradeDto.setTeacherId(grade.getTeacher().getId());
            gradeDtos.add(gradeDto);
        }
        return gradeDtos;
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
}
