package com.lenglish.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenglish.domain.Answer;
import com.lenglish.domain.CustomerUser;
import com.lenglish.domain.Exam;
import com.lenglish.domain.ExamLog;
import com.lenglish.domain.Lesson;
import com.lenglish.domain.LessonLog;
import com.lenglish.domain.Question;
import com.lenglish.domain.enumeration.QuestionType;
import com.lenglish.repository.AnswerRepository;
import com.lenglish.repository.CustomerUserRepository;
import com.lenglish.repository.ExamLogRepository;
import com.lenglish.repository.ExamRepository;
import com.lenglish.repository.LessonLogRepository;
import com.lenglish.repository.LessonRepository;
import com.lenglish.repository.QuestionRepository;
import com.lenglish.service.dto.AnswerDTO;
import com.lenglish.service.dto.ResultLessonDTO;
import com.lenglish.service.mapper.AnswerMapper;
import com.lenglish.service.util.DateTimeUtil;

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

	private final UserService userService;

	private final CustomerUserRepository customerUserRepository;

	private final LessonRepository lessonRepository;

	private final LessonLogRepository lessonLogRepository;

	private final ExamRepository examRepository;

	private final ExamLogRepository examLogRepository;

	public AnswerService(AnswerRepository answerRepository, AnswerMapper answerMapper, UserService userService,
			CustomerUserRepository customerUserRepository, QuestionRepository questionRepository,
			LessonRepository lessonRepository, LessonLogRepository lessonLogRepository, ExamRepository examRepository,
			ExamLogRepository examLogRepository) {
		this.answerRepository = answerRepository;
		this.answerMapper = answerMapper;
		this.userService = userService;
		this.customerUserRepository = customerUserRepository;
		this.questionRepository = questionRepository;
		this.lessonRepository = lessonRepository;
		this.lessonLogRepository = lessonLogRepository;
		this.examRepository = examRepository;
		this.examLogRepository = examLogRepository;
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

	public ResultLessonDTO submitAnswer(List<AnswerDTO> answerDTOs, Long lessonId) {
		int total = answerDTOs.size();
		int success = 0;
		if (total <= 0) {
			return new ResultLessonDTO(0, 0);
		}
		// get lesson
		Lesson lesson = lessonRepository.findOne(lessonId);
		// count question
		int totalSelection = 0;
		int totalListening = 0;
		int totalTranslation = 0;
		int totalSpeech = 0;
		int selection = 0;
		int listening = 0;
		int translation = 0;
		int speech = 0;
		QuestionType questionType = null;
		List<Question> questions = questionRepository.findAllByLesson(lesson);
		for (Question question : questions) {
			if (QuestionType.SELECTION == question.getQuestionType()) {
				totalSelection++;
			}
			if (QuestionType.LISTENING == question.getQuestionType()) {
				totalListening++;
			}
			if (QuestionType.TRANSLATION == question.getQuestionType()) {
				totalTranslation++;
			}
			if (QuestionType.SPEECH == question.getQuestionType()) {
				totalSpeech++;
			}
		}

		// create
		for (AnswerDTO answerDTO : answerDTOs) {
			questionType = checkAnswer(answerDTO);
			if (questionType != null) {
				success++;
				if (QuestionType.SELECTION == questionType) {
					selection++;
				}
				if (QuestionType.LISTENING == questionType) {
					listening++;
				}
				if (QuestionType.TRANSLATION == questionType) {
					translation++;
				}
				if (QuestionType.SPEECH == questionType) {
					speech++;
				}
			}
		}
		LessonLog lessonLog = new LessonLog();
		lessonLog.setComplete(success * 5 / (total == 0 ? 1 : total));
		lessonLog.setSelection(selection * 5 / (totalSelection == 0 ? 1 : totalSelection));
		lessonLog.setListening(listening * 5 / (totalListening == 0 ? 1 : totalListening));
		lessonLog.setSpeech(speech * 5 / (totalSpeech == 0 ? 1 : totalSpeech));
		lessonLog.setTranslation(translation * 5 / (totalTranslation == 0 ? 1 : totalTranslation));
		lessonLog.setLesson(lesson);
		lessonLog.setUser(userService.getUserWithAuthorities());
		lessonLog.setCreateDate(DateTimeUtil.now());
		// >= 4 is success
		if (lessonLog.getComplete() >= 4) {
			CustomerUser customerUser = customerUserRepository.findOneByUser(userService.getUserWithAuthorities());
			if (customerUser != null) {
				customerUser.setPoint(customerUser.getPoint() + 10);
				customerUser.setTodayPoint(customerUser.getTodayPoint() + 10);

				// increase one level for user if current level == level of lesson
				if (customerUser.getLevel() == lesson.getLevel() && checkCompleteLevel(lesson.getLevel())) {
					customerUser.setLevel(customerUser.getLevel() + 1);
					log.debug("up level " + customerUser.getLevel());
				}
				lessonLog.setPoint(10);
			}
			customerUserRepository.save(customerUser);
		}
		lessonLogRepository.save(lessonLog);
		ResultLessonDTO resultLessonDTO = new ResultLessonDTO();
		resultLessonDTO.setTotal(totalSpeech);
		resultLessonDTO.setSuccess(success);
		resultLessonDTO.setComplete(success * 5 / (total == 0 ? 1 : total));
		resultLessonDTO.setListening(listening * 5 / (totalListening == 0 ? 1 : totalListening));
		resultLessonDTO.setSelection(selection * 5 / (totalSelection == 0 ? 1 : totalSelection));
		resultLessonDTO.setTranslation(translation * 5 / (totalTranslation == 0 ? 1 : totalTranslation));
		resultLessonDTO.setSpeech(speech * 5 / (totalSpeech == 0 ? 1 : totalSpeech));
		return resultLessonDTO;
	}

	private QuestionType checkAnswer(AnswerDTO answerDTO) {
		Question question = null;
		List<Answer> answers = null;
		if (answerDTO == null)
			return null;
		question = questionRepository.findOne(answerDTO.getQuestionId());
		if (question != null) {
			if (QuestionType.LISTENING == (question.getQuestionType())) {
				if (question.getContent() != null
						&& (question.getContent().trim()).equalsIgnoreCase(answerDTO.getContent().trim())) {
					return question.getQuestionType();
				} else {
					return null;
				}
			}
			answers = answerRepository.findAllByQuestionAndResult(question, true);
			if (QuestionType.SELECTION == (question.getQuestionType())) {
				for (Answer answer : answers) {
					if (answer.getId() == answerDTO.getId()) {
						return question.getQuestionType();
					} else {
						return null;
					}
				}
			}
			if (QuestionType.TRANSLATION == (question.getQuestionType())) {
				for (Answer answer : answers) {
					log.debug(answer.getContent());
					if ((answer.getContent().trim()).equalsIgnoreCase(answerDTO.getContent().trim())) {
						return question.getQuestionType();
					} else {
						return null;
					}
				}
			}
			if (QuestionType.SPEECH == (question.getQuestionType())) {
				for (Answer answer : answers) {
					if (answer.getContent().equalsIgnoreCase(answerDTO.getContent())) {
						return question.getQuestionType();
					} else {
						return null;
					}
				}
			}
		}

		return null;
	}

	public ResultLessonDTO submitAnswerExam(List<AnswerDTO> answerDTOs, Long examId) {
		int total = answerDTOs.size();
		int success = 0;
		if (total <= 0) {
			return new ResultLessonDTO(0, 0);
		}
		// count question
		int totalSelection = 0;
		int totalListening = 0;
		int totalTranslation = 0;
		int totalSpeech = 0;
		int selection = 0;
		int listening = 0;
		int translation = 0;
		int speech = 0;
		QuestionType questionType = null;
		Exam exam = examRepository.findOne(examId);
		List<Question> questions = questionRepository.findAllByExams(exam);
		for (Question question : questions) {
			if (QuestionType.SELECTION == question.getQuestionType()) {
				totalSelection++;
			}
			if (QuestionType.LISTENING == question.getQuestionType()) {
				totalListening++;
			}
			if (QuestionType.TRANSLATION == question.getQuestionType()) {
				totalTranslation++;
			}
			if (QuestionType.SPEECH == question.getQuestionType()) {
				totalSpeech++;
			}
		}

		// create
		for (AnswerDTO answerDTO : answerDTOs) {
			questionType = checkAnswer(answerDTO);
			if (questionType != null) {
				success++;
				if (QuestionType.SELECTION == questionType) {
					selection++;
				}
				if (QuestionType.LISTENING == questionType) {
					listening++;
				}
				if (QuestionType.TRANSLATION == questionType) {
					translation++;
				}
				if (QuestionType.SPEECH == questionType) {
					speech++;
				}
			}
		}
		ExamLog examLog = new ExamLog();
		examLog.setComplete(success * 5 / (total == 0 ? 1 : total));
		examLog.setUser(userService.getUserWithAuthorities());
		examLog.setCreateDate(DateTimeUtil.now());
		// >= 4 is success
		if (examLog.getComplete() >= 4) {
			CustomerUser customerUser = customerUserRepository.findOneByUser(userService.getUserWithAuthorities());
			if (customerUser != null) {
				customerUser.setPoint(customerUser.getPoint() + 30);
				customerUser
						.setTodayPoint(customerUser.getTodayPoint() + (exam.getPoint() == null ? 0 : exam.getPoint()));
				// increase one level for user if current level == level of lesson
				if (customerUser.getLevel() <= exam.getLevel()) {
					customerUser.setLevel(exam.getLevel() + 1);
				}
			}
			customerUserRepository.save(customerUser);
			examLog.setPoint(30);
		}
		examLogRepository.save(examLog);
		ResultLessonDTO resultLessonDTO = new ResultLessonDTO();
		resultLessonDTO.setTotal(totalSpeech);
		resultLessonDTO.setSuccess(success);
		resultLessonDTO.setComplete(success * 5 / (total == 0 ? 1 : total));
		resultLessonDTO.setListening(listening * 5 / (totalListening == 0 ? 1 : totalListening));
		resultLessonDTO.setSelection(selection * 5 / (totalSelection == 0 ? 1 : totalSelection));
		resultLessonDTO.setTranslation(translation * 5 / (totalTranslation == 0 ? 1 : totalTranslation));
		resultLessonDTO.setSpeech(speech * 5 / (totalSpeech == 0 ? 1 : totalSpeech));
		return resultLessonDTO;
	}

	public boolean checkCompleteLevel(int level) {
		List<Lesson> lessons = lessonRepository.findAllByLevel(level);
		for (Lesson lesson : lessons) {
			LessonLog lessonLog = lessonLogRepository
					.findTopByUserAndLessonOrderByCreateDateDesc(userService.getUserWithAuthorities(), lesson);
			if (lessonLog.getComplete() < 4) {
				return false;
			}
		}
		return true;
	}

}
