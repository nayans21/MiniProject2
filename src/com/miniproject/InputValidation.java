package com.miniproject;
import java.util.regex.*;

public class InputValidation {

	public static boolean isNameValid(String str) {
		for(int i=0;i<str.length();i++) {
			if(!Character.isLetter(str.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static boolean isMobileNumberValid(String str) {
		for(int i=0;i<str.length();i++) {
			if(!Character.isDigit(str.charAt(i)))
				return false;
		}
		if(str.length()!=10)
			return false;
		return true;
	}
	
	public static boolean isUsernameValid(String str) {
		String usernamePattern = "^[a-zA-z][a-zA-z0-9]*[.#$_]{0,1}[a-zA-z0-9]+$";
		Pattern p = Pattern.compile(usernamePattern);
		Matcher match = p.matcher(str);
		if(match.matches())
			return true;
		else
			return false;
	}
	
	public static boolean isPasswordValid(String str) {
		String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,10})";
		Pattern p = Pattern.compile(passwordPattern);
		Matcher match = p.matcher(str);
		if(match.matches())
			return true;
		else
			return false;
		
	}
	
	public static boolean isMailIDValid(String str) {
		String mailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(mailPattern);
		Matcher match = p.matcher(str);
		if(match.matches())
			return true;
		else
			return false;
	}
}
