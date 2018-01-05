package com.lenglish.service.dto;

public class ActionDTO {
	private int listening;
	private int selection;
	private int translation;
	private int speech;

	private int[] points;

	public ActionDTO() {
	}

	public ActionDTO(int listening, int selection, int translation, int speech, int[] points) {
		this.listening = listening;
		this.selection = selection;
		this.translation = translation;
		this.speech = speech;
		this.points = points;
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

	public int[] getPoints() {
		return points;
	}

	public void setPoints(int[] points) {
		this.points = points;
	}

}
