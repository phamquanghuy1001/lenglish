package com.lenglish.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.service.ExamLogService;
import com.lenglish.web.rest.errors.BadRequestAlertException;
import com.lenglish.web.rest.util.HeaderUtil;
import com.lenglish.web.rest.util.PaginationUtil;
import com.lenglish.service.dto.ExamLogDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExamLog.
 */
@RestController
@RequestMapping("/api")
public class ExamLogResource {

    private final Logger log = LoggerFactory.getLogger(ExamLogResource.class);

    private static final String ENTITY_NAME = "examLog";

    private final ExamLogService examLogService;

    public ExamLogResource(ExamLogService examLogService) {
        this.examLogService = examLogService;
    }

    /**
     * POST  /exam-logs : Create a new examLog.
     *
     * @param examLogDTO the examLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examLogDTO, or with status 400 (Bad Request) if the examLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-logs")
    @Timed
    public ResponseEntity<ExamLogDTO> createExamLog(@Valid @RequestBody ExamLogDTO examLogDTO) throws URISyntaxException {
        log.debug("REST request to save ExamLog : {}", examLogDTO);
        if (examLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new examLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamLogDTO result = examLogService.save(examLogDTO);
        return ResponseEntity.created(new URI("/api/exam-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-logs : Updates an existing examLog.
     *
     * @param examLogDTO the examLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examLogDTO,
     * or with status 400 (Bad Request) if the examLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the examLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-logs")
    @Timed
    public ResponseEntity<ExamLogDTO> updateExamLog(@Valid @RequestBody ExamLogDTO examLogDTO) throws URISyntaxException {
        log.debug("REST request to update ExamLog : {}", examLogDTO);
        if (examLogDTO.getId() == null) {
            return createExamLog(examLogDTO);
        }
        ExamLogDTO result = examLogService.save(examLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-logs : get all the examLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examLogs in body
     */
    @GetMapping("/exam-logs")
    @Timed
    public ResponseEntity<List<ExamLogDTO>> getAllExamLogs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ExamLogs");
        Page<ExamLogDTO> page = examLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-logs/:id : get the "id" examLog.
     *
     * @param id the id of the examLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-logs/{id}")
    @Timed
    public ResponseEntity<ExamLogDTO> getExamLog(@PathVariable Long id) {
        log.debug("REST request to get ExamLog : {}", id);
        ExamLogDTO examLogDTO = examLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examLogDTO));
    }

    /**
     * DELETE  /exam-logs/:id : delete the "id" examLog.
     *
     * @param id the id of the examLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamLog(@PathVariable Long id) {
        log.debug("REST request to delete ExamLog : {}", id);
        examLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
