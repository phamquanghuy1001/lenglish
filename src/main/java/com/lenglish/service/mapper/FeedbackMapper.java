package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.FeedbackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Feedback and its DTO FeedbackDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, UserMapper.class})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, Feedback> {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    FeedbackDTO toDto(Feedback feedback); 

    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "userId", target = "user")
    Feedback toEntity(FeedbackDTO feedbackDTO);

    default Feedback fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setId(id);
        return feedback;
    }
}
