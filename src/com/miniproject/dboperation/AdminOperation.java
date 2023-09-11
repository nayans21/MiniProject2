package com.miniproject.dboperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.miniproject.Question;
import com.miniproject.QuestionOption;
import com.miniproject.QuizResult;
import com.miniproject.Student;

public class AdminOperation {

	public ArrayList<QuizResult> getStudentScoreByID(int studentID) throws SQLException, NoDataFoundException {
		Connection con = new DatabaseConnection().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM quizresult WHERE studentid=?";
		ArrayList<QuizResult> score = new ArrayList<QuizResult>();
		ps = con.prepareStatement(sql);
		ps.setInt(1, studentID);
		rs = ps.executeQuery();
		
		if(rs.next()==false) {
			String errMessage = "No Student found with the studentid as "+studentID;
			rs.close();
			ps.close();
			con.close();
			throw new NoDataFoundException(errMessage);
		}
		else {
				rs.previous();
				
				while(rs.next()) {
					QuizResult quizResult = new QuizResult();
					quizResult.setQuizID(rs.getInt("quizid"));
					quizResult.setScore(rs.getInt("score"));
					quizResult.setQuizDate(rs.getDate("quizdate"));
					score.add(quizResult);
				}
				rs.close();
				ps.close();
				con.close();
			}
		return score;
	}
	
	public void addQuestion(Question question) throws SQLException {
		Connection con = new DatabaseConnection().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String questionSql = "INSERT INTO question(questionstring) VALUES(?)";
		ps = con.prepareStatement(questionSql);
		ps.setString(1, question.getQuestionString());
		ps.executeUpdate();
		ps.close();
		
		String query = "SELECT questionid FROM question WHERE questionstring=?";
		int qID;
		ps = con.prepareStatement(query);
		ps.setString(1, question.getQuestionString());
		rs = ps.executeQuery();
		rs.next();
		qID = rs.getInt("questionid");
		rs.close();
		ps.close();
		
		ArrayList<QuestionOption> optionList = new ArrayList<QuestionOption>();
		optionList.add(question.getOption1());
		optionList.add(question.getOption2());
		optionList.add(question.getOption3());
		optionList.add(question.getOption4());
		
		String optionSql = "INSERT INTO questionoption(optionname,questionid,isanswer) VALUES(?,?,?)";
		ps = con.prepareStatement(optionSql);
		for(int i=0;i<optionList.size();i++) {
			ps.setString(1, optionList.get(i).getOptionName());
			ps.setInt(2, qID);
			ps.setString(3, optionList.get(i).getIsAnswer());
			ps.executeUpdate();
		}
		ps.close();
		con.close();
	}
	
	public LinkedList<HashMap<String,Object>> getStudentsScoreByASCOrder() throws SQLException {
		Connection con = new DatabaseConnection().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT student.studentid,firstname,lastname,score,quizdate "
				+ "FROM student,quizresult "
				+ "WHERE student.studentid=quizresult.studentid "
				+ "ORDER BY score,firstname,lastname ASC";
		
		LinkedList<HashMap<String,Object>> studentsWithScore = new LinkedList<HashMap<String,Object>>();
		HashMap<String,Object> quizDetails = null;
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			
			Student student = new Student();
			student.setStudentID(rs.getInt("studentid"));
			student.setFirstName(rs.getString("firstname"));
			student.setLastName(rs.getString("lastname"));
			
			QuizResult quizResult = new QuizResult();
			quizResult.setScore(rs.getInt("score"));
			quizResult.setQuizDate(rs.getDate("quizDate"));
			
			quizDetails = new HashMap<String,Object>();
			
			quizDetails.put("studentInfo", student);
			quizDetails.put("quizInfo", quizResult);
			
			studentsWithScore.add(quizDetails);
			}
		
		return studentsWithScore;
		}
		
}
