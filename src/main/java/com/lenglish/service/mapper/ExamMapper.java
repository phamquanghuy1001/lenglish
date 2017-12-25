package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.ExamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exam and its DTO ExamDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {

    

    @Mapping(target = "questions", ignore = true)
    Exam toEntity(ExamDTO examDTO);

    default Exam fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exam exam = new Exam();
        exam.setId(id);
        return exam;
    }
}
