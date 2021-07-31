package org.tonylin.robotframework.expect;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import expect4j.Expect4j;
import expect4j.ExpectUtils;

public class ExpectUtilTest {

	private ExpectUtil expectUtil = null;
	
	@Before
	public void setUp() throws Exception {
		expectUtil = new ExpectUtil();
	}

	@After
	public void tearDown() throws Exception {
		expectUtil.closeSpawn();
	}

	@Test
	public void test() throws Exception {
		expectUtil.spawn("/bin/sh");
		expectUtil.setExpectTimeout(5);
		expectUtil.sendCommand("echo 1345");
		Assert.assertEquals("PASS", expectUtil.expectShouldContain("1345"));
		Assert.assertEquals("FAIL", expectUtil.expectShouldContain("5678"));
		expectUtil.sendCommand("exit");
	}

}
