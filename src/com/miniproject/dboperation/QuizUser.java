package com.miniproject.dboperation;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuizUser {

	private String username;
	private String password;
	private int userid;
	private String usertype;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public static QuizUser checkCredentials(QuizUser quizUser)
	 throws SQLException,NoDataFoundException{
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM quizuser WHERE username=? AND pwd=?";
		String errMessage = "";
		QuizUser user = null;
		
		
			ps = con.prepareStatement(sql);
			ps.setString(1, quizUser.getUsername());
			ps.setString(2, quizUser.getPassword());
			rs = ps.executeQuery();
		
			if(rs.next()==false) {
				errMessage ="Username or Password is not valid";
				rs.close();
				ps.close();
				con.close();
				throw new NoDataFoundException(errMessage);
			}
		
			else {
				user = new QuizUser();
				user.setUserid(rs.getInt("userid"));
				user.setUsername(rs.getString("username"));
				user.setUsertype(rs.getString("usertype"));
				rs.close();
				ps.close();
				con.close();
			}
		return user;
	}
	
	public static int addNewStudentQuizUser(QuizUser quizUser) throws SQLException, DataInsertionException {
		
		DatabaseConnection obj = new DatabaseConnection();
		Connection con = obj.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT username FROM quizuser WHERE username=?";
		String userInsert = "INSERT INTO quizuser(username,pwd,usertype) VALUES(?,?,?)";
		int rows =0;
		int userID=0;
		
		ps = con.prepareStatement(query);
		ps.setString(1, quizUser.getUsername());
		rs = ps.executeQuery();
		
		if(rs.next()==true) {
			String errMessage="Given Username is already present";
			rs.close();
			ps.close();
			con.close();
			throw new DataInsertionException(errMessage);
		}
		
		else {
			rs.close();
			ps.close();	
			ps = con.prepareStatement(userInsert);
			ps.setString(1, quizUser.getUsername());
			ps.setString(2, quizUser.getPassword());
			ps.setString(3, "student");
			rows = ps.executeUpdate();
			ps.close();
		}
		if(rows==0) {
			String errMessage = "User's record is not able to add in 'quizuser' table";
			con.close();
			throw new DataInsertionException(errMessage);
		}
		
		query = "SELECT userid FROM quizuser WHERE username=? and pwd=? and usertype='student'";
		ps = con.prepareStatement(query);
		ps.setString(1, quizUser.getUsername());
		ps.setString(2, quizUser.getPassword());
		rs = ps.executeQuery();
		rs.next();
		userID = rs.getInt("userID");
		rs.close();
		ps.close();
		con.close();
		return userID;
		
	}
	
}