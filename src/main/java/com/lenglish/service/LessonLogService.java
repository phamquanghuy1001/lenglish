package com.lenglish.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenglish.domain.Lesson;
import com.lenglish.domain.LessonLog;
import com.lenglish.domain.User;
import com.lenglish.repository.LessonLogRepository;
import com.lenglish.service.dto.LessonLogDTO;
import com.lenglish.service.mapper.LessonLogMapper;


/**
 * Service Implementation for managing LessonLog.
 */
@Service
@Transactional
public class LessonLogService {

    private final Logger log = LoggerFactory.getLogger(LessonLogService.class);

    private final LessonLogRepository lessonLogRepository;

    private final LessonLogMapper lessonLogMapper;

    public LessonLogService(LessonLogRepository lessonLogRepository, LessonLogMapper lessonLogMapper) {
        this.lessonLogRepository = lessonLogRepository;
        this.lessonLogMapper = lessonLogMapper;
    }

    /**
     * Save a lessonLog.
     *
     * @param lessonLogDTO the entity to save
     * @return the persisted entity
     */
    public LessonLogDTO save(LessonLogDTO lessonLogDTO) {
        log.debug("Request to save LessonLog : {}", lessonLogDTO);
        LessonLog lessonLog = lessonLogMapper.toEntity(lessonLogDTO);
        lessonLog = lessonLogRepository.save(lessonLog);
        return lessonLogMapper.toDto(lessonLog);
    }

    /**
     *  Get all the lessonLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LessonLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LessonLogs");
        return lessonLogRepository.findAll(pageable)
            .map(lessonLogMapper::toDto);
    }

    /**
     *  Get one lessonLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LessonLogDTO findOne(Long id) {
        log.debug("Request to get LessonLog : {}", id);
        LessonLog lessonLog = lessonLogRepository.findOne(id);
        return lessonLogMapper.toDto(lessonLog);
    }

    /**
     *  Delete the  lessonLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LessonLog : {}", id);
        lessonLogRepository.delete(id);
    }
    
    public Page<LessonLogDTO> findAllByLessonAndUser(Pageable pageable, Lesson lesson, User user) {
		log.debug("Request to get all LessonLogs");
        return lessonLogRepository.findAllByLessonAndUser(pageable, lesson, user)
            .map(lessonLogMapper::toDto);
	}
}
