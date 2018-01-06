package com.lenglish.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.domain.LessonLog;
import com.lenglish.service.ActionService;
import com.lenglish.service.ExamService;
import com.lenglish.service.LessonLogService;
import com.lenglish.service.LessonService;
import com.lenglish.service.PostService;
import com.lenglish.service.UserService;
import com.lenglish.service.dto.ActionDTO;
import com.lenglish.service.dto.DashboardDTO;

@RestController
@RequestMapping("/api")
public class ActionResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	private final ActionService actionService;

	public ActionResource(ActionService actionService) {
		this.actionService = actionService;
	}

	@GetMapping("/action")
	@Timed
	public ResponseEntity<ActionDTO> getData() {
		log.debug("REST request to get dashboard data");
		ActionDTO actionDTO = actionService.queryAction();
		return new ResponseEntity<>(actionDTO, HttpStatus.OK);
	}

}
