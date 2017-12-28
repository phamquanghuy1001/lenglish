package com.lenglish.service.dto;

import java.io.Serializable;

public class ResultLessonDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private int success;

	public ResultLessonDTO() {
	}

	public ResultLessonDTO(int total, int success) {
		this.total = total;
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

}
