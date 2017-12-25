package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.ExamLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamLog and its DTO ExamLogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ExamMapper.class})
public interface ExamLogMapper extends EntityMapper<ExamLogDTO, ExamLog> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "exam.id", target = "examId")
    ExamLogDTO toDto(ExamLog examLog); 

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "examId", target = "exam")
    ExamLog toEntity(ExamLogDTO examLogDTO);

    default ExamLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamLog examLog = new ExamLog();
        examLog.setId(id);
        return examLog;
    }
}
