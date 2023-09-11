package com.miniproject.dboperation;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.miniproject.Question;
import com.miniproject.QuestionOption;
import com.miniproject.QuizResult;
import com.miniproject.Student;

public class StudentOperation{

	public void addStudentDetails(Student student,QuizUser user) 
			throws SQLException,DataInsertionException {
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		int flag=0,rows;
		
		
		String studInsert = "INSERT INTO student(userid,firstname,lastname,city,mailid,mobilenumber) ";
		studInsert = studInsert + "VALUES(?,?,?,?,?,?)";
		
		
		int userID = QuizUser.addNewStudentQuizUser(user);
				
		ps = con.prepareStatement(studInsert);
		ps.setInt(1, userID);
		ps.setString(2, student.getFirstName());
		ps.setString(3, student.getLastName());
		ps.setString(4, student.getCity());
		ps.setString(5, student.getMailID());
		ps.setString(6, student.getMobileNumber());
	    rows = ps.executeUpdate();
		ps.close();
		con.close();
				
		if(rows!=1) {
			String errMessage = "Data is not inserted in Student table";
			throw new DataInsertionException(errMessage);
		}
	}
	
	public ArrayList<Question> getQuizQuestions() throws SQLException,NoDataFoundException {
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet optionRS = null;
		
		ArrayList<Question> questionList= new ArrayList<Question>();
		
		String sql = "SELECT * FROM question";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		if(rs.next()==false) {
			rs.close();
			ps.close();
			con.close();
			String errMessage = "No record found in the question table";
			throw new NoDataFoundException(errMessage);
		}
		else {
			rs.previous();
			String query;
			Question question = null;
			QuestionOption questionOption = null;
			
			while(rs.next()) {
				
				question = new Question();
				question.setQuestionID(rs.getInt("questionid"));
				question.setQuestionString(rs.getString("questionstring"));
				
				query = "SELECT * FROM questionoption WHERE questionid=?";
				ps = con.prepareStatement(query);
				ps.setInt(1, question.getQuestionID());
				optionRS = ps.executeQuery();
				int optionCnt=0;
				while(optionRS.next()) {
					optionCnt++;
					questionOption = new QuestionOption();
					questionOption.setOptionName(optionRS.getString("optionname"));
					questionOption.setIsAnswer(optionRS.getString("isanswer"));
					if(optionCnt==1)
						question.setOption1(questionOption);
					else if(optionCnt==2)
						question.setOption2(questionOption);
					else if(optionCnt==3)
						question.setOption3(questionOption);
					else if(optionCnt==4)
						question.setOption4(questionOption);
				}
				questionList.add(question);
			}
			optionRS.close();
			rs.close();
			ps.close();
			con.close();
		}
		return questionList;	
	}
	
	public void storeQuizResult(int studentID,int score) 
			throws SQLException,DataInsertionException, NoDataFoundException {
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO quizresult(studentid,score,quizdate) ";
		sql = sql + "VALUES(?,?,CURRENT_DATE())";
		ps = con.prepareStatement(sql);
		ps.setInt(1, studentID);
		ps.setInt(2, score);
		int rows = ps.executeUpdate();
		ps.close();
		con.close();
		
		if(rows!=1) {
			String errMessage = "Score is not inserted into the database";
			throw new DataInsertionException(errMessage);
		}		

	}
	
	public ArrayList<QuizResult> displayQuizResult(int studentID) 
			throws SQLException, NoDataFoundException {
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<QuizResult> score = new ArrayList<QuizResult>();
		
		String sql = "SELECT * FROM quizresult WHERE studentid=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, studentID);
		rs = ps.executeQuery();
		
		if(rs.next()==false) {
			String errMessage = "No score found for the student";
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
	
	public int getStudentID(int userID) throws SQLException, NoDataFoundException {
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int studentID = 0;
		
		String sql = "SELECT studentid FROM student WHERE userid=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, userID);
		rs = ps.executeQuery();
		
		if(rs.next()==false) {
			String errMessage = "No studentid found for the student";
			rs.close();
			ps.close();
			con.close();
			throw new NoDataFoundException(errMessage);
		}
		else {
			
			studentID = rs.getInt("studentid");
			rs.close();
			ps.close();
			con.close();
			}
		
		return studentID;
	}
}


			