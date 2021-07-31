package org.tonylin.robotframework;

public class StaticStringUtil {
	/**
	 * Translate the aInputString to the lower case.
	 * 
	 * @param aInputString
	 * @return
	 */
	public String translateToLowercase(String aInputString){
		return StringUtil.toLowerCase(aInputString);
	}
	
	/**
	 * Translate the aInputString to the upper case.
	 * 
	 * @param aInputString
	 * @return
	 */
	public String translateToUppercase(String aInputString){
		return StringUtil.toUpperCase(aInputString);
	}
	
	/**
	 * Split the aInputString by the aRegex.
	 * 
	 * @param aInputString
	 * @param aRegex
	 * @return
	 */
	public String[] splitString(String aInputString, String aRegex){
		return StringUtil.splitString(aInputString, aRegex);
	}
}
