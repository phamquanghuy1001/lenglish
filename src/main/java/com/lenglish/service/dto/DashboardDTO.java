package com.lenglish.service.dto;

import java.util.List;

public class DashboardDTO {
	private long totalUser;
	private long totalLesson;
	private long totalExam;
	private long totalPost;
	private List<UserDTO> lastedUser;

	public DashboardDTO() {
	}

	public long getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(long totalUser) {
		this.totalUser = totalUser;
	}

	public long getTotalLesson() {
		return totalLesson;
	}

	public void setTotalLesson(long totalLesson) {
		this.totalLesson = totalLesson;
	}

	public long getTotalExam() {
		return totalExam;
	}

	public void setTotalExam(long totalExam) {
		this.totalExam = totalExam;
	}

	public long getTotalPost() {
		return totalPost;
	}

	public void setTotalPost(long totalPost) {
		this.totalPost = totalPost;
	}

	public List<UserDTO> getLastedUser() {
		return lastedUser;
	}

	public void setLastedUser(List<UserDTO> lastedUser) {
		this.lastedUser = lastedUser;
	}

}
