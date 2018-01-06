package com.lenglish.service.dto;

import java.io.Serializable;

public class ResultLessonDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private int success;
	private int complete;
	private int listening;
	private int selection;
	private int translation;
	private int speech;

	public ResultLessonDTO() {
	}

	public ResultLessonDTO(int total, int success) {
		this.total = total;
		this.success = success;
	}

	public ResultLessonDTO(int total, int success, int complete, int listening, int selection, int translation,
			int speech) {
		this.total = total;
		this.success = success;
		this.complete = complete;
		this.listening = listening;
		this.selection = selection;
		this.translation = translation;
		this.speech = speech;
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

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public int getListening() {
		return listening;
	}

	public void setListening(int listening) {
		this.listening = listening;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public int getTranslation() {
		return translation;
	}

	public void setTranslation(int translation) {
		this.translation = translation;
	}

	public int getSpeech() {
		return speech;
	}

	public void setSpeech(int speech) {
		this.speech = speech;
	}

}
