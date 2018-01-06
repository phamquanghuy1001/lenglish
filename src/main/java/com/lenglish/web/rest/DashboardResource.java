package com.lenglish.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.service.ExamService;
import com.lenglish.service.LessonService;
import com.lenglish.service.PostService;
import com.lenglish.service.UserService;
import com.lenglish.service.dto.DashboardDTO;

@RestController
@RequestMapping("/api")
public class DashboardResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	private final UserService userService;

	private final PostService postService;

	private final LessonService lessonService;

	private final ExamService examService;

	public DashboardResource(UserService userService, PostService postService, LessonService lessonService,
			ExamService examService) {
		this.userService = userService;
		this.postService = postService;
		this.lessonService = lessonService;
		this.examService = examService;
	}

	@GetMapping("/dashboard")
	@Timed
	public ResponseEntity<DashboardDTO> getData() {
		log.debug("REST request to get dashboard data");
		DashboardDTO dashboardDTO = new DashboardDTO();
		dashboardDTO.setTotalUser(userService.countUser());
		dashboardDTO.setTotalPost(postService.countPost());
		dashboardDTO.setTotalLesson(lessonService.countLesson());
		dashboardDTO.setTotalExam(examService.countExam());
		Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "createdDate"));
		dashboardDTO.setLastedUser(userService.getAllManagedUsers(pageable).getContent());
		return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
	}

}
