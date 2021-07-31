package org.tonylin.robotframework;
import org.tonylin.robotframework.StringUtil;


public class HybridStringUtil {
	public String[] getKeywordNames() {
		return new String[] { 
				"translateToLowercase",
				"translateToUppercase" };
	}
	
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
}
