package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.LessonLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LessonLog and its DTO LessonLogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LessonMapper.class})
public interface LessonLogMapper extends EntityMapper<LessonLogDTO, LessonLog> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "lesson.id", target = "lessonId")
    LessonLogDTO toDto(LessonLog lessonLog); 

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "lessonId", target = "lesson")
    LessonLog toEntity(LessonLogDTO lessonLogDTO);

    default LessonLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        LessonLog lessonLog = new LessonLog();
        lessonLog.setId(id);
        return lessonLog;
    }
}
