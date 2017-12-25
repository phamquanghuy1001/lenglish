package com.lenglish.service;

import com.lenglish.domain.ExamLog;
import com.lenglish.repository.ExamLogRepository;
import com.lenglish.service.dto.ExamLogDTO;
import com.lenglish.service.mapper.ExamLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamLog.
 */
@Service
@Transactional
public class ExamLogService {

    private final Logger log = LoggerFactory.getLogger(ExamLogService.class);

    private final ExamLogRepository examLogRepository;

    private final ExamLogMapper examLogMapper;

    public ExamLogService(ExamLogRepository examLogRepository, ExamLogMapper examLogMapper) {
        this.examLogRepository = examLogRepository;
        this.examLogMapper = examLogMapper;
    }

    /**
     * Save a examLog.
     *
     * @param examLogDTO the entity to save
     * @return the persisted entity
     */
    public ExamLogDTO save(ExamLogDTO examLogDTO) {
        log.debug("Request to save ExamLog : {}", examLogDTO);
        ExamLog examLog = examLogMapper.toEntity(examLogDTO);
        examLog = examLogRepository.save(examLog);
        return examLogMapper.toDto(examLog);
    }

    /**
     *  Get all the examLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExamLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamLogs");
        return examLogRepository.findAll(pageable)
            .map(examLogMapper::toDto);
    }

    /**
     *  Get one examLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExamLogDTO findOne(Long id) {
        log.debug("Request to get ExamLog : {}", id);
        ExamLog examLog = examLogRepository.findOne(id);
        return examLogMapper.toDto(examLog);
    }

    /**
     *  Delete the  examLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExamLog : {}", id);
        examLogRepository.delete(id);
    }
}
