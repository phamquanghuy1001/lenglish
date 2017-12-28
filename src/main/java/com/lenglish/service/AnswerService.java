package com.lenglish.service;

import com.lenglish.domain.Answer;
import com.lenglish.domain.Question;
import com.lenglish.domain.enumeration.QuestionType;
import com.lenglish.repository.AnswerRepository;
import com.lenglish.repository.QuestionRepository;
import com.lenglish.service.dto.AnswerDTO;
import com.lenglish.service.dto.QuestionDTO;
import com.lenglish.service.dto.ResultAnswerDTO;
import com.lenglish.service.dto.ResultLessonDTO;
import com.lenglish.service.mapper.AnswerMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Answer.
 */
@Service
@Transactional
public class AnswerService {

	private final Logger log = LoggerFactory.getLogger(AnswerService.class);

	private final AnswerRepository answerRepository;

	private final QuestionRepository questionRepository;

	private final AnswerMapper answerMapper;

	public AnswerService(AnswerRepository answerRepository, AnswerMapper answerMapper,
			QuestionRepository questionRepository) {
		this.answerRepository = answerRepository;
		this.answerMapper = answerMapper;
		this.questionRepository = questionRepository;
	}

	/**
	 * Save a answer.
	 *
	 * @param answerDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public AnswerDTO save(AnswerDTO answerDTO) {
		log.debug("Request to save Answer : {}", answerDTO);
		Answer answer = answerMapper.toEntity(answerDTO);
		answer = answerRepository.save(answer);
		return answerMapper.toDto(answer);
	}

	/**
	 * Get all the answers.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<AnswerDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Answers");
		return answerRepository.findAll(pageable).map(answerMapper::toDto);
	}

	/**
	 * Get one answer by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public AnswerDTO findOne(Long id) {
		log.debug("Request to get Answer : {}", id);
		Answer answer = answerRepository.findOne(id);
		return answerMapper.toDto(answer);
	}

	/**
	 * Delete the answer by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Answer : {}", id);
		answerRepository.delete(id);
	}

	public Page<AnswerDTO> findAllByQuestion(Pageable pageable, Question question) {
		log.debug("Request to get all Answers by Question");
		return answerRepository.findAllByQuestion(pageable, question).map(answerMapper::toDto);
	}

	public ResultLessonDTO submitAnswer(List<AnswerDTO> answerDTOs) {
		int total = answerDTOs.size();
		int success = 0;
		for (AnswerDTO answerDTO : answerDTOs) {
			if (checkAnswer(answerDTO)) {
				success++;
			}
		}
		return new ResultLessonDTO(total, success);
	}

	private boolean checkAnswer(AnswerDTO answerDTO) {
		Question question = null;
		List<Answer> answers = null;
		question = questionRepository.findOne(answerDTO.getQuestionId());
		if (question != null) {
			log.error("---------------------Check---------------:" + question.getQuestionType());
			if (QuestionType.LISTENING == (question.getQuestionType())) {
				if (question.getContent() != null && question.getContent().equals(answerDTO.getContent())) {
					return true;
				} else {
					return false;
				}
			}
			answers = answerRepository.findAllByQuestionAndResult(question, true);
			if (QuestionType.SELECTION == (question.getQuestionType())) {
				for (Answer answer : answers) {
					if (answer.getId() == answerDTO.getId()) {
						return true;
					}
				}
				return false;
			}
			if (QuestionType.TRANSLATION == (question.getQuestionType())) {
				log.error("---------------------TRANSLATION---------------");
				log.debug(question.getContent());
				for (Answer answer : answers) {
					log.debug(answer.getContent());
					if (answer.getContent().equalsIgnoreCase(answerDTO.getContent())) {
						return true;
					}
				}
				return false;
			}
			if (QuestionType.SPEECH == (question.getQuestionType())) {
				for (Answer answer : answers) {
					if (answer.getContent().equalsIgnoreCase(answerDTO.getContent())) {
						return true;
					}
				}
				return false;
			}
			log.error("---------------------No---------------");
		}

		return false;
	}
}
