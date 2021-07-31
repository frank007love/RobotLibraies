package org.tonylin.robotframework;

public interface RobotFrameworkDynamicAPI {
	String[] getKeywordNames();
	Object runKeyword(String name, Object[] arguments);
	String[] getKeywordArguments(String name);
	String getKeywordDocumentation(String name);
}
