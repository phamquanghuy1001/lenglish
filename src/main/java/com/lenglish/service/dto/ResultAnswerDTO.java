package com.lenglish.service.dto;

public class ResultAnswerDTO {
	private boolean success;
	private AnswerDTO answer;

	public ResultAnswerDTO() {
	}

	public ResultAnswerDTO(boolean success, AnswerDTO answer) {
		this.success = success;
		this.answer = answer;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public AnswerDTO getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerDTO answer) {
		this.answer = answer;
	}

}
