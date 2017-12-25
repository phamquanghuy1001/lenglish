package com.lenglish.service.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtil {
	
	public static ZonedDateTime now() {
		return ZonedDateTime.now(ZoneId.systemDefault());
	}

}
