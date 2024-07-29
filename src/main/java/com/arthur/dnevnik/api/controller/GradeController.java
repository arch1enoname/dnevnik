package com.arthur.dnevnik.api.controller;

import com.arthur.dnevnik.api.Entity.Grade;
import com.arthur.dnevnik.api.dto.GradeDto;
import com.arthur.dnevnik.api.repository.GradeRepository;
import com.arthur.dnevnik.api.service.GradeService;
import com.arthur.dnevnik.api.service.GradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {
    private final GradeServiceImpl gradeService;

    @Autowired
    public GradeController(GradeServiceImpl gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/rateStudent")
    public ResponseEntity<Grade> rateStudent(@RequestBody GradeDto grade) {
        gradeService.rateStudent(grade);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getGrades/{studentId}/{subjectId}")
    public ResponseEntity<List<GradeDto>> getGrades(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return new ResponseEntity<>(gradeService.getGradesByStudentIdAndSubjectId(studentId,subjectId), HttpStatus.OK);
    }

    @GetMapping("/getWeightedAverageGrade/{studentId}/{subjectId}")
    public ResponseEntity<Double> getWeightedAverageGrade(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return new ResponseEntity<>(gradeService.getWeightedAverageGradeByStudentIdAndSubjectId(studentId, subjectId), HttpStatus.OK);
    }

    @GetMapping("/getAverageGrade/{studentId}/{subjectId}")
    public ResponseEntity<Double> getAverageGrade(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return new ResponseEntity<>(gradeService.getAverageGradeByStudentIdAndSubjectId(studentId, subjectId), HttpStatus.OK);
    }

}
