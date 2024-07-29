package com.arthur.dnevnik.api.service;

import com.arthur.dnevnik.api.Entity.Subject;
import com.arthur.dnevnik.api.dto.SubjectDto;
import com.arthur.dnevnik.api.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectDto createSubject(SubjectDto subjectDto) {
        if (subjectRepository.findByName(subjectDto.getName()).isPresent()) {
            throw new RuntimeException("Subject with name " + subjectDto.getName() + " already exists");
        }
        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subjectRepository.save(subject);
        return subjectDto;

    }

    public SubjectDto getSubjectById(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return new SubjectDto(subject.get().getName());
        } else {
            throw new RuntimeException("Subject with id " + id + " does not exist");
        }
    }

}
