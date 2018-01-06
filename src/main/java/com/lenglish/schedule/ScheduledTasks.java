package com.lenglish.schedule;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lenglish.service.CustomerUserService;
import com.lenglish.service.util.DateTimeUtil;
import com.lenglish.web.rest.AccountResource;

@Component
public class ScheduledTasks {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	private final CustomerUserService customerUserService;

	public ScheduledTasks(CustomerUserService customerUserService) {
		this.customerUserService = customerUserService;
	}

	@Scheduled(fixedRate = 60000)
	public void reportCurrentTime() {
		log.info("The time is now {}", DateTimeUtil.now());
		ZonedDateTime zonedDateTime = DateTimeUtil.now();
		if (zonedDateTime.getMinute() == 0 && zonedDateTime.getHour() == 0) {
			customerUserService.resetTodayPoint();
		}
	}
}
