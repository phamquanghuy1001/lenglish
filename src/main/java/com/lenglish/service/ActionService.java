package com.lenglish.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenglish.domain.Exam;
import com.lenglish.domain.ExamLog;
import com.lenglish.domain.Lesson;
import com.lenglish.domain.LessonLog;
import com.lenglish.domain.User;
import com.lenglish.repository.ExamLogRepository;
import com.lenglish.repository.ExamRepository;
import com.lenglish.repository.LessonLogRepository;
import com.lenglish.repository.LessonRepository;
import com.lenglish.service.dto.ActionDTO;
import com.lenglish.service.util.DateTimeUtil;

@Service
@Transactional
public class ActionService {

	private final Logger log = LoggerFactory.getLogger(LessonLogService.class);

	private final UserService userService;

	private final LessonRepository lessonRepository;

	private final LessonLogRepository lessonLogRepository;

	private final ExamRepository examRepository;

	private final ExamLogRepository examLogRepository;

	public ActionService(UserService userService, LessonRepository lessonRepository,
			LessonLogRepository lessonLogRepository, ExamRepository examRepository,
			ExamLogRepository examLogRepository) {
		this.userService = userService;
		this.lessonRepository = lessonRepository;
		this.lessonLogRepository = lessonLogRepository;
		this.examRepository = examRepository;
		this.examLogRepository = examLogRepository;
	}

	public ActionDTO queryAction() {
		int listening = 0;
		int selection = 0;
		int translation = 0;
		int speech = 0;
		int[] points = new int[7];
		ActionDTO actionDTO = new ActionDTO();
//		List<Exam> exams = examRepository.findAll();
		List<ExamLog> examLogs = new ArrayList<>();
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
//			lessonLogs = lessonLogRepository.findByUserAndCreateDateBetween(user, start, end);
//			// point of lessonlog
//			if (lessonLogs != null) {
//				for (LessonLog lessonLog : lessonLogs) {
//					tmpPoint += lessonLog.getPoint() == null ? 0 : lessonLog.getPoint();
//				}
//			}
//			examLogs = examLogRepository.findByUserAndCreateDateBetween(user, start, end);
//			// point of examLog
//			if (examLogs != null) {
//				for (ExamLog examLog : examLogs) {
//					tmpPoint += examLog.getPoint() == null ? 0 : examLog.getPoint();
//				}
//			}
			points[6 - i] = tmpPoint;
			end = start;
			start = start.minusDays(1);
		}
		actionDTO.setPoints(points);

		return actionDTO;
	}

}
