package org.tonylin.robotframework;

public class StringUtil {
	
	public static String toLowerCase(String aInputString){
		return aInputString.toLowerCase();
	}
	
	public static String toUpperCase(String aInputString){
		return aInputString.toUpperCase();
	}
	
	public static String[] splitString(String aInputString, String aRegex){
		return aInputString.split(aRegex);
	}
	
	public static String[] splitString(String aInputString){
		return splitString(aInputString, ",");
	}
}
