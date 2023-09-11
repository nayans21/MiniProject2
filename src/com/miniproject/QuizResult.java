package com.miniproject;

import java.util.Date;

public class QuizResult {

	private int quizID;
	private int studentID;
	private int score;
	private Date quizDate;
	
	public int getQuizID() {
		return quizID;
	}
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getQuizDate() {
		return quizDate;
	}
	public void setQuizDate(Date quizDate) {
		this.quizDate = quizDate;
	}
	
	
}
