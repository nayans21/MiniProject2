package com.miniproject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.miniproject.dboperation.AdminOperation;
import com.miniproject.dboperation.DataInsertionException;
import com.miniproject.dboperation.NoDataFoundException;
import com.miniproject.dboperation.QuizUser;
import com.miniproject.dboperation.StudentOperation;

public class QuizApplication {

	public static QuizUser quizUser = null;
	public static Scanner scanner = null;
	
	public static void setScanner() {
		
		scanner = new Scanner(System.in);
		
	}
	
	public void performInputValidationForStudent(Student student, QuizUser user)
			throws InvalidInputException {
		
		if(!InputValidation.isNameValid(student.getFirstName()))
			throw new InvalidInputException("First Name is not in valid format");
		
		if(!InputValidation.isNameValid(student.getLastName()))
			throw new InvalidInputException("Last Name is not in valid format");
		
		if(!InputValidation.isNameValid(student.getCity()))
			throw new InvalidInputException("City Name is not in valid format");
		
		if(!InputValidation.isMobileNumberValid(student.getMobileNumber()))
			throw new InvalidInputException("Mobile Number is not in valid format");
		
		if(!InputValidation.isUsernameValid(user.getUsername()))
			throw new InvalidInputException("Username is not in valid format");
		
		if(!InputValidation.isPasswordValid(user.getPassword()))
			throw new InvalidInputException("Password is not in valid format");
		
		if(!InputValidation.isMailIDValid(student.getMailID()))
			throw new InvalidInputException("Email is not in valid format");
	}
	
	public void doStudentRegistration() 
			throws SQLException, NoDataFoundException, DataInsertionException, InvalidInputException {
		
		Student student = new Student();
		QuizUser user = new QuizUser();
		
		System.out.print("Enter First Name : ");
		student.setFirstName(scanner.next());
		System.out.print("Enter Last Name : ");
		student.setLastName(scanner.next());
		System.out.print("Enter Username : ");
		user.setUsername(scanner.next());
		System.out.print("Enter Password : ");
		user.setPassword(scanner.next());
		System.out.print("Enter city : ");
		student.setCity(scanner.next());
		System.out.print("Enter MailID : ");
		student.setMailID(scanner.next());
		System.out.print("Enter Mobile Number : ");
		student.setMobileNumber(scanner.next());
		
		performInputValidationForStudent(student,user);
		StudentOperation studentOperation = new StudentOperation();
		studentOperation.addStudentDetails(student,user);
		System.out.println();
		System.out.println("Student Registration is Done Successfully");
	}
	
	public void getLoginDetails() throws SQLException, NoDataFoundException {
		
		quizUser = new QuizUser();
		System.out.print("Enter Username : ");
		quizUser.setUsername(scanner.next());
		System.out.print("Enter Password : ");
		quizUser.setPassword(scanner.next());
		
		quizUser = QuizUser.checkCredentials(quizUser);
		
		/*
		Console console = System.console();
		quizUser = new QuizUser();
		System.out.print("Enter Username:");
        quizUser.setUsername(console.readLine());
        System.out.print("Enter Password:");
        char ch[] = console.readPassword();
        quizUser.setPassword(String.valueOf(ch));
        quizUser = QuizUser.checkCredentials(quizUser);
		
		*/
	}
	
	public void getQuizResult(StudentOperation studentOperation) throws SQLException, NoDataFoundException {
		
		int studentID = studentOperation.getStudentID(quizUser.getUserid());
		ArrayList<QuizResult> quizResult = studentOperation.displayQuizResult(studentID);
		System.out.println();
		
		System.out.println("Your Score is as follows : ");
		System.out.println();
		
		String str = "  QuizID  Score  QuizDate    ";
		int cnt = str.length();
		// Line 1
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");
		
		System.out.print("| ");
		System.out.print("QuizID");
		System.out.print(" | ");
		System.out.print("Score");
		System.out.print(" | ");
		System.out.print(" QuizDate ");
		System.out.println(" |");
		
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");
		
		
		// Line 2
		for(QuizResult result : quizResult) {
			System.out.print("| ");
			System.out.printf("%6s",result.getQuizID());
			System.out.print(" | ");
			System.out.printf("%5s",result.getScore());
			System.out.print(" | ");
			System.out.printf("%10s",result.getQuizDate());
			System.out.print(" | ");
			System.out.println();
		}
		
		// Line 3
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.print("+");
		
	}
	
	public int startQuiz(StudentOperation studentOperation) throws SQLException, NoDataFoundException {
		ArrayList<Question> quizQuestions = studentOperation.getQuizQuestions();
		int score = 0;
		int studentAnswer=0;
		int answer=0;
		for(Question question : quizQuestions) {
			System.out.println();
			System.out.println(question.getQuestionString());
			for(int i=1;i<=4;i++) {
				System.out.print(i + ". ");
				if(i==1)
					System.out.println(question.getOption1().getOptionName());
				else if(i==2)
					System.out.println(question.getOption2().getOptionName());
				else if(i==3)
					System.out.println(question.getOption3().getOptionName());
				else if(i==4)
					System.out.println(question.getOption4().getOptionName());
			}
			String value="";
			for(int i=1;i<=4;i++) {
				if(i==1) {
					value = question.getOption1().getIsAnswer();
					if(value.compareTo("yes")==0)
						answer = 1;
				}
				else if(i==2) {
					value = question.getOption2().getIsAnswer();
					if(value.compareTo("yes")==0)
						answer = 2;
				}
				else if(i==3) {
					value = question.getOption3().getIsAnswer();
					if(value.compareTo("yes")==0)
						answer = 3;
				}
				else if(i==4) {
					value = question.getOption4().getIsAnswer();
					if(value.compareTo("yes")==0)
						answer = 4;
				}
			}
			System.out.print("Type Option No : ");
			studentAnswer = scanner.nextInt();
			if(studentAnswer == answer)
				score++;
		}
		System.out.println();
		System.out.println("You have Successfully Completed the Quiz.");
		return score;
	}
	
	public void insertQuizResultIntoDB(StudentOperation studentOperation,int score) 
			throws SQLException, DataInsertionException, NoDataFoundException {
		
		int studentID = studentOperation.getStudentID(quizUser.getUserid());
		studentOperation.storeQuizResult(studentID, score);
		System.out.println();
		System.out.println("Your score is added into databse.");
	}
	
	public void performStudentOperations() {
		int choice;
		System.out.println();
		String title = "Welcome User "+ quizUser.getUsername();
		displayTitle(title,title.length()+4);
		StudentOperation studentOperation = new StudentOperation();
		int studentQuizScore=0;
		boolean studentPlayedQuiz = false;
		do {
			System.out.println("\nChoose Any of the below Option");
			System.out.println("1. Display the List of Questions");
			System.out.println("2. Store Quiz Result into Database");
			System.out.println("3. Display Quiz Result");
			System.out.println("4. Logout");
			System.out.print("Enter your Option : ");
			choice = scanner.nextInt();
			
			try {
				switch(choice) {
				case 1:studentQuizScore = startQuiz(studentOperation);
					   studentPlayedQuiz = true;
				       break;
				case 2:if(studentPlayedQuiz) {
					   	insertQuizResultIntoDB(studentOperation,studentQuizScore);
					   	studentPlayedQuiz = false;
					   	studentQuizScore = 0;
						}
				       else {
				    	   System.out.println();
				    	   System.out.println("Please start the quiz first.");
				       }
				       break;
				case 3:getQuizResult(studentOperation);
					   break;
				case 4:studentOperation = null;
			           quizUser = null;
			           System.out.println();
				       System.out.println("You have successfully logged out.");
				       System.out.println("\n*********************************************************");
				       break;
				}
			}catch(NoDataFoundException e) {
				System.out.println();
				System.out.println(e.getMessage());
			}catch(SQLException e) {
				System.out.println();
				e.printStackTrace();
			}catch(Exception e) {
				System.out.println();
				e.printStackTrace();
			}
			
			
		}while(choice!=4);
	}
	
	public void displayStudentsScoreByASCOrder(AdminOperation admin) throws SQLException {
		
		LinkedList<HashMap<String,Object>> list = admin.getStudentsScoreByASCOrder();
				 
		System.out.println();
		String str = "  FirstName  LastName  Score  QuizDate    ";
		int cnt = str.length();
		cnt = cnt + 32;
		// Line 1
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");	
		System.out.print("| ");
		System.out.printf("%20s","FirstName");
		System.out.print(" | ");
		System.out.printf("%20s","LastName");
		System.out.print(" | ");
		System.out.printf("%8s","Score");
		System.out.print(" | ");
		System.out.printf("%15s","Quiz Date");
		System.out.print(" |");
		System.out.println();
		
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");
		
		for(HashMap<String,Object> ele : list) {
			
			Student student = (Student) ele.get("studentInfo");
			QuizResult quizResult = (QuizResult) ele.get("quizInfo");
			System.out.print("| ");
			System.out.printf("%20s",student.getFirstName());
			System.out.print(" | ");
			System.out.printf("%20s",student.getLastName());
			System.out.print(" | ");
			System.out.printf("%8s",quizResult.getScore());
			System.out.print(" | ");
			System.out.printf("%15s",quizResult.getQuizDate());
			System.out.print(" |");
			System.out.println();
		}
		
		// Line 3
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.print("+");		
	}
	
	public void fetchStudentScoreByID(AdminOperation admin) throws SQLException, NoDataFoundException {
		
		System.out.print("Enter Student ID : ");
		int id = scanner.nextInt();
		int score=0;
		ArrayList<QuizResult> quizResult = admin.getStudentScoreByID(id);
		System.out.println();
		String str = "  QuizID  Score  QuizDate    ";
		int cnt = str.length();
		// Line 1
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");
		
		System.out.print("| ");
		System.out.print("QuizID");
		System.out.print(" | ");
		System.out.print("Score");
		System.out.print(" | ");
		System.out.print(" QuizDate ");
		System.out.println(" |");
		
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");
		
		
		// Line 2
		for(QuizResult result : quizResult) {
			System.out.print("| ");
			System.out.printf("%6s",result.getQuizID());
			System.out.print(" | ");
			System.out.printf("%5s",result.getScore());
			System.out.print(" | ");
			System.out.printf("%10s",result.getQuizDate());
			System.out.print(" | ");
			System.out.println();
		}
		
		// Line 3
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.print("+");
	}
	
	public void insertQuestionToDB(AdminOperation admin) throws SQLException, InvalidInputException {
		
		scanner.nextLine();
		System.out.println("Enter question : ");
		String questionString = scanner.nextLine();
		Question question = new Question();
		question.setQuestionString(questionString.trim());
		System.out.println("Write 4 options one by one");
		for(int i=1;i<=4;i++) {
			System.out.print(i+ ". ");
			QuestionOption option = new QuestionOption();
			option.setOptionName(scanner.nextLine().trim());
			option.setIsAnswer("no");
			if(i==1) 
				question.setOption1(option);
			else if(i==2)
				question.setOption2(option);
			else if(i==3)
				question.setOption3(option);
			else if(i==4)
				question.setOption4(option);
		}
		System.out.print("Enter Answer : ");
		String answer = scanner.nextLine();
		
		answer = answer.trim();
		if(question.getQuestionString().isEmpty() || answer.isEmpty()) {
			throw new InvalidInputException("Question or Answer cannot be only space or empty.");
		}
		ArrayList<String> optionNames = new ArrayList<String>();
		optionNames.add(question.getOption1().getOptionName());
		optionNames.add(question.getOption2().getOptionName());
		optionNames.add(question.getOption3().getOptionName());
		optionNames.add(question.getOption4().getOptionName());
		
		for(int i=0;i<4;i++) {
			String value = optionNames.get(i);
			if(value.isEmpty())
				throw new InvalidInputException("Options cannot be only space or empty");
		}
		for(int i=0;i<4;i++) {
			String value = optionNames.get(i);
			if(optionNames.indexOf(value)!=optionNames.lastIndexOf(value))
				throw new InvalidInputException("Duplicate options are not allowed");
		}
		if(!optionNames.contains(answer)) {
			throw new InvalidInputException("Answer is not match with any of the options");
		}
		
		if(answer.compareTo(question.getOption1().getOptionName())==0)
			question.getOption1().setIsAnswer("yes");
		else if(answer.compareTo(question.getOption2().getOptionName())==0)
			question.getOption2().setIsAnswer("yes");
		else if(answer.compareTo(question.getOption3().getOptionName())==0)
			question.getOption3().setIsAnswer("yes");
		else if(answer.compareTo(question.getOption4().getOptionName())==0)
			question.getOption4().setIsAnswer("yes");
		
		admin.addQuestion(question);
		System.out.println();
		System.out.println("Question is added successfully.");
	}
	
	public void performAdminOperations(){
		int choice;
		System.out.println();
		String title = "Welcome Admin "+ quizUser.getUsername();
		displayTitle(title,title.length()+4);
		AdminOperation admin = new AdminOperation();
		do {
			System.out.println();
			System.out.println("Choose Any of the below Option");
			System.out.println("1. Display all students score as per ascending order");
			System.out.println("2. Fetch student score by using id");
			System.out.println("3. Add question with 4 options into database");
			System.out.println("4. Logout");
			System.out.print("Enter your Option : ");
			choice = scanner.nextInt();
			try {
				switch(choice) {
				case 1:displayStudentsScoreByASCOrder(admin);break;
				case 2:fetchStudentScoreByID(admin);break;
				case 3:insertQuestionToDB(admin);break;
				case 4:admin = null;
				       quizUser = null;
				       System.out.println();
					   System.out.println("You have successfully Logout.");
					   System.out.println("\n******************************************************");
					   break;
				}
			}catch(NoDataFoundException e) {
				System.out.println();
				System.out.println(e.getMessage());
			}catch(SQLException e) {
				System.out.println();
				System.out.println(e.getMessage());
			}catch(Exception e) {
				System.out.println();
				System.out.println(e.getMessage());
			}
			
		}while(choice!=4);
	}
	
	public void displayTitle(String title,int cnt) {
	
		// Line 1
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.println("+");
		
		// Line 2
		System.out.print("|");
		System.out.print("  ");
		System.out.print(title);
		System.out.print("  ");
		System.out.print("|");
		System.out.println();
		
		// Line 3
		System.out.print("+");
		for(int i=1;i<=cnt;i++)
			System.out.print("-");
		System.out.print("+");
	}
	
	public static void main(String[] args) {
		
		QuizApplication app = new QuizApplication();
		String title = "Welcome To Quiz Application";
		app.displayTitle(title,title.length()+4);
		System.out.println();
		int choice;
		QuizApplication.setScanner();
		
		do {
			System.out.println();
			System.out.println("Choose Any of the below Option");
			System.out.println("1. Student Registration");
			System.out.println("2. User Login");
			System.out.println("3. Exit");
			System.out.print("Enter your Option : ");
			choice = scanner.nextInt();
			try {
				switch(choice) {
				case 1 : app.doStudentRegistration();
						 break;
				case 2 : app.getLoginDetails();
						 if(quizUser.getUsertype().equals("student"))
							 app.performStudentOperations();
						 else if(quizUser.getUsertype().equals("admin"))
							 app.performAdminOperations();
						 break;
				case 3 : System.out.println();
						 System.out.println("Thanks for Using Quiz Application.");
						 break;
					}

			}catch(NoDataFoundException e) {
				System.out.println();
				System.out.println(e.getMessage());
			}catch(DataInsertionException e) {
				System.out.println();
				System.out.println(e.getMessage());
			}catch(SQLException e) {
				System.out.println();
				e.printStackTrace();
			}catch(InvalidInputException e) {
				System.out.println();
				System.out.println(e.getMessage());
			}catch(Exception e) {
				System.out.println();
				e.printStackTrace();
			}
		}while(choice!=3);
	}
}
