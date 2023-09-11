package com.miniproject;

public class Question {
	
	private int questionID;
	private String questionString;
	private QuestionOption option1;
	private QuestionOption option2;
	private QuestionOption option3;
	private QuestionOption option4;
	
	public QuestionOption getOption1() {
		return option1;
	}
	public void setOption1(QuestionOption option1) {
		this.option1 = option1;
	}
	public QuestionOption getOption2() {
		return option2;
	}
	public void setOption2(QuestionOption option2) {
		this.option2 = option2;
	}
	public QuestionOption getOption3() {
		return option3;
	}
	public void setOption3(QuestionOption option3) {
		this.option3 = option3;
	}
	public QuestionOption getOption4() {
		return option4;
	}
	public void setOption4(QuestionOption option4) {
		this.option4 = option4;
	}
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public String getQuestionString() {
		return questionString;
	}
	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}
}
