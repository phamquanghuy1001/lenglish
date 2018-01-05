package com.lenglish.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.lenglish.repository.LessonRepository;
import com.lenglish.service.dto.ActionDTO;
import com.lenglish.service.dto.LessonLogDTO;
import com.lenglish.service.mapper.LessonLogMapper;
import com.lenglish.service.util.DateTimeUtil;

/**
 * Service Implementation for managing LessonLog.
 */
@Service
@Transactional
public class LessonLogService {

	private final Logger log = LoggerFactory.getLogger(LessonLogService.class);

	private final LessonLogRepository lessonLogRepository;

	private final UserService userService;

	private final LessonRepository lessonRepository;

	private final LessonLogMapper lessonLogMapper;

	public LessonLogService(LessonLogRepository lessonLogRepository, UserService userService,
			LessonRepository lessonRepository, LessonLogMapper lessonLogMapper) {
		this.lessonLogRepository = lessonLogRepository;
		this.userService = userService;
		this.lessonRepository = lessonRepository;
		this.lessonLogMapper = lessonLogMapper;
	}

	/**
	 * Save a lessonLog.
	 *
	 * @param lessonLogDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public LessonLogDTO save(LessonLogDTO lessonLogDTO) {
		log.debug("Request to save LessonLog : {}", lessonLogDTO);
		lessonLogDTO.setCreateDate(DateTimeUtil.now());
		LessonLog lessonLog = lessonLogMapper.toEntity(lessonLogDTO);
		lessonLog = lessonLogRepository.save(lessonLog);
		return lessonLogMapper.toDto(lessonLog);
	}

	/**
	 * Get all the lessonLogs.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<LessonLogDTO> findAll(Pageable pageable) {
		log.debug("Request to get all LessonLogs");
		return lessonLogRepository.findAll(pageable).map(lessonLogMapper::toDto);
	}

	/**
	 * Get one lessonLog by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public LessonLogDTO findOne(Long id) {
		log.debug("Request to get LessonLog : {}", id);
		LessonLog lessonLog = lessonLogRepository.findOne(id);
		return lessonLogMapper.toDto(lessonLog);
	}

	/**
	 * Delete the lessonLog by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete LessonLog : {}", id);
		lessonLogRepository.delete(id);
	}

	public Page<LessonLogDTO> findAllByLessonAndUser(Pageable pageable, Lesson lesson, User user) {
		log.debug("Request to get all LessonLogs");
		return lessonLogRepository.findAllByLessonAndUser(pageable, lesson, user).map(lessonLogMapper::toDto);
	}

	public ActionDTO queryAction() {
		int listening = 0;
		int selection = 0;
		int translation = 0;
		int speech = 0;
		int[] points = new int[7];
		ActionDTO actionDTO = new ActionDTO();
		// calculate skill
		List<Lesson> lessons = lessonRepository.findAll();
		List<LessonLog> lessonLogs = new ArrayList<>();
		LessonLog lessonLogTmp = null;
		User user = userService.getUserWithAuthorities();
		for (Lesson lesson : lessons) {
			lessonLogTmp = lessonLogRepository.findTopByUserAndLessonOrderByCreateDateDesc(user, lesson);
			if (lessonLogTmp != null) {
				lessonLogs.add(lessonLogTmp);
			}
		}
		for (LessonLog lessonLog : lessonLogs) {
			listening += lessonLog.getListening();
			selection += lessonLog.getSelection();
			translation += lessonLog.getTranslation();
			speech += lessonLog.getSpeech();
		}
		int size = lessonLogs.size() == 0 ? 1 : lessonLogs.size();
		actionDTO.setListening(listening / size);
		actionDTO.setSelection(selection / size);
		actionDTO.setTranslation(translation / size);
		actionDTO.setSpeech(speech / size);
		actionDTO.setListening(listening / size);
		ZonedDateTime end = DateTimeUtil.now();
		ZonedDateTime start = ZonedDateTime.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 0, 0, 0, 0,
				end.getZone());
		int tmpPoint = 0;
		for (int i = 0; i < 7; i++) {
			tmpPoint = 0;
			lessonLogs = lessonLogRepository.findByUserAndCreateDateBetween(user, start, end);
			if (lessonLogs == null) {
				points[6 - i] = 0;
			} else {
				for (LessonLog lessonLog : lessonLogs) {
					tmpPoint += lessonLog.getPoint() == null ? 0 : lessonLog.getPoint();
				}
				points[6 - i] = tmpPoint;
			}
			end = start;
			start = start.minusDays(1);
		}
		actionDTO.setPoints(points);

		return actionDTO;
	}
}
