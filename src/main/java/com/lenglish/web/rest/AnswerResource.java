package com.lenglish.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.domain.Question;
import com.lenglish.service.AnswerService;
import com.lenglish.service.dto.AnswerDTO;
import com.lenglish.service.dto.ResultLessonDTO;
import com.lenglish.service.util.DateTimeUtil;
import com.lenglish.web.rest.errors.BadRequestAlertException;
import com.lenglish.web.rest.util.HeaderUtil;
import com.lenglish.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Answer.
 */
@RestController
@RequestMapping("/api")
public class AnswerResource {

	private final Logger log = LoggerFactory.getLogger(AnswerResource.class);

	private static final String ENTITY_NAME = "answer";

	private final AnswerService answerService;

	public AnswerResource(AnswerService answerService) {
		this.answerService = answerService;
	}

	/**
	 * POST /answers : Create a new answer.
	 *
	 * @param answerDTO
	 *            the answerDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         answerDTO, or with status 400 (Bad Request) if the answer has already
	 *         an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/answers")
	@Timed
	public ResponseEntity<AnswerDTO> createAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
		log.debug("REST request to save Answer : {}", answerDTO);
		if (answerDTO.getId() != null) {
			throw new BadRequestAlertException("A new answer cannot already have an ID", ENTITY_NAME, "idexists");
		}
		answerDTO.setCreateDate(DateTimeUtil.now());
		AnswerDTO result = answerService.save(answerDTO);
		return ResponseEntity.created(new URI("/api/answers/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /answers : Updates an existing answer.
	 *
	 * @param answerDTO
	 *            the answerDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         answerDTO, or with status 400 (Bad Request) if the answerDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the answerDTO
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/answers")
	@Timed
	public ResponseEntity<AnswerDTO> updateAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
		log.debug("REST request to update Answer : {}", answerDTO);
		if (answerDTO.getId() == null) {
			return createAnswer(answerDTO);
		}
		AnswerDTO result = answerService.save(answerDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, answerDTO.getId().toString())).body(result);
	}

	/**
	 * GET /answers : get all the answers.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of answers in
	 *         body
	 */
	@GetMapping("/answers")
	@Timed
	public ResponseEntity<List<AnswerDTO>> getAllAnswers(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Answers");
		Page<AnswerDTO> page = answerService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/answers");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /answers/:id : get the "id" answer.
	 *
	 * @param id
	 *            the id of the answerDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the answerDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/answers/{id}")
	@Timed
	public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long id) {
		log.debug("REST request to get Answer : {}", id);
		AnswerDTO answerDTO = answerService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(answerDTO));
	}

	/**
	 * DELETE /answers/:id : delete the "id" answer.
	 *
	 * @param id
	 *            the id of the answerDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/answers/{id}")
	@Timed
	public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
		log.debug("REST request to delete Answer : {}", id);
		answerService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	// new code
	/**
	 * GET /answers : get all the answers.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of answers in
	 *         body
	 */
	@GetMapping("/answers_by_question/{id}")
	@Timed
	public ResponseEntity<List<AnswerDTO>> getAllAnswersByQuestion(@ApiParam Pageable pageable, @PathVariable Long id) {
		log.debug("REST request to get a page of Answers by Question");
		Question question = new Question();
		question.setId(id);
		Page<AnswerDTO> page = answerService.findAllByQuestion(pageable, question);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/answers_by_question/" + id);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PostMapping("/submit_answers")
	@Timed
	public ResponseEntity<ResultLessonDTO> submitAnswer(@Valid @RequestBody List<AnswerDTO> answerDTOs) {
		log.debug("Submit Answers of Lesson");
		ResultLessonDTO resultLessonDTO = answerService.submitAnswer(answerDTOs);
		return new ResponseEntity<>(resultLessonDTO , HttpStatus.OK);
	}
	
	@PostMapping("/check_answers")
	@Timed
	public ResponseEntity<ResultLessonDTO> checkAnswer(@Valid @RequestBody AnswerDTO answerDTOs) {
		log.debug("Check Answer");
		return new ResponseEntity<>(new ResultLessonDTO(0, 0) , HttpStatus.OK);
	}
	
}
