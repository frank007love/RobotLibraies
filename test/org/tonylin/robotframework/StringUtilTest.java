package org.tonylin.robotframework;

import java.lang.reflect.Constructor;

import org.junit.Test;


public class StringUtilTest {

	@Test
	public void testPrivateReflection() throws Exception { 
		
		Class<StringUtil> stringUtilClass = StringUtil.class; 
		Constructor<StringUtil> stringUtilConst = stringUtilClass.getConstructor();
		StringUtil stringUtil = stringUtilConst.newInstance();
	}
	
}
