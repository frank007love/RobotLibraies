package org.tonylin.robotframework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicStringUtil implements RobotFrameworkDynamicAPI {

	final private static Map<String, String> DOCUMENTATIONS;
	final private static Map<String, String[]> ARGUMENTS;
	final private static String[] SUPPORTED_METHODS_NAME = new String[]{
		"translate_to_lowercase",
		"translate_to_uppercase",
		"split_string"
	};
	final private static String[] SUPPORTED_METHODS_DOC = new String[]{
		"Translate the input string to the lower case.",
		"Translate the input string to the upper case.",
		"Split the input string by regular expression."
	};	
	final private static String[][] SUPPORTED_METHODS_ARGUMENT = new String[][]{
		new String[]{"aInputString"},
		new String[]{"aInputString"},
		new String[]{"aInputString", "aRegex=,"}
	};	
	
	
	static {
		DOCUMENTATIONS = new HashMap<String, String>();
		ARGUMENTS = new HashMap<String, String[]>();
		for( int i = 0 ; i < SUPPORTED_METHODS_NAME.length ; i++ ){
			DOCUMENTATIONS.put(SUPPORTED_METHODS_NAME[i], 
					SUPPORTED_METHODS_DOC[i]);
			ARGUMENTS.put(SUPPORTED_METHODS_NAME[i], 
					SUPPORTED_METHODS_ARGUMENT[i]);
		}
	}
	
	@Override
	public String[] getKeywordNames() {
		return Arrays.copyOf(SUPPORTED_METHODS_NAME, 
				SUPPORTED_METHODS_NAME.length);
	}

	@Override
	public Object runKeyword(String name, Object[] arguments) {
		if( name.equals(SUPPORTED_METHODS_NAME[0]) ){
			return StringUtil.toLowerCase(arguments[0].toString());
		} else if( name.equals(SUPPORTED_METHODS_NAME[1])){
			return StringUtil.toUpperCase(arguments[0].toString());
		} else if( name.equals(SUPPORTED_METHODS_NAME[2])){
			if( arguments.length == 1 ){
				return StringUtil.splitString(arguments[0].toString());	
			}
			return StringUtil.splitString(arguments[0].toString(),
					arguments[1].toString());
		}
		throw new RuntimeException("Not support keyword: " + name);
	}

	@Override
	public String[] getKeywordArguments(String name) {
		if( !ARGUMENTS.containsKey(name)){
			throw new RuntimeException("Not support keyword: " + name); 
		}
		return ARGUMENTS.get(name);
	}

	@Override
	public String getKeywordDocumentation(String name) {
		if( !DOCUMENTATIONS.containsKey(name)){
			throw new RuntimeException("Not support keyword: " + name); 
		}
		return DOCUMENTATIONS.get(name);
	}

	
}
