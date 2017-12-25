package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {LessonMapper.class, ExamMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(source = "lesson.id", target = "lessonId")
    QuestionDTO toDto(Question question); 

    @Mapping(source = "lessonId", target = "lesson")
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
