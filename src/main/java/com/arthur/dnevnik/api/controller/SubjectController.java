package com.arthur.dnevnik.api.controller;

import com.arthur.dnevnik.api.dto.SubjectDto;
import com.arthur.dnevnik.api.service.SubjectService;
import com.arthur.dnevnik.api.service.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {
    private final SubjectServiceImpl subjectService;

    @Autowired
    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/createSubject")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        return new ResponseEntity<>(subjectService.createSubject(subjectDto), HttpStatus.CREATED);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> getSubject(@PathVariable Long subjectId) {
        return new ResponseEntity<>(subjectService.getSubjectById(subjectId), HttpStatus.OK);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
