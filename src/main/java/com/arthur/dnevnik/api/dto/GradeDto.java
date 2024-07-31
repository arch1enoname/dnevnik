package com.arthur.dnevnik.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GradeDto {
    Integer value;
    Integer importance;
    Long teacherId;
    Long studentId;
    Long subjectId;
    Date dateOfRate;
}
