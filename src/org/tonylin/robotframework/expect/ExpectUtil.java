package org.tonylin.robotframework.expect;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import expect4j.Expect4j;
import expect4j.ExpectUtils;

public class ExpectUtil {
	public static final String ROBOT_LIBRARY_VERSION = "1.0";
	public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";
	
	private HashMap<Long, Expect4j> mExpectObjectMap;
	private Expect4j mCurrentExpectObj = null;
	private Random mRandom = new Random(new Date().getTime());
	
	public ExpectUtil(){
		mExpectObjectMap = new HashMap<Long, Expect4j>();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		for( Expect4j expectObj : mExpectObjectMap.values()){
			expectObj.close();
		}
	}
	
	/**
	 * Create a new spawn with aCmd, and return the spawn id.
	 * 
	 * @param aCmd
	 * @return
	 * @throws Exception
	 */
	public long spawn(String aCmd) throws Exception{
		Expect4j expectObj = ExpectUtils.spawn(aCmd); 
		mCurrentExpectObj = expectObj;
		
		long spawnId = mRandom.nextLong();
		
		mExpectObjectMap.put(spawnId, expectObj);
		return spawnId;
	}
	
	/**
	 * Close current spawn.
	 */
	public void closeSpawn(){
		if( mCurrentExpectObj != null )
			mCurrentExpectObj.close();
	}
	
	/**
	 * Send key entry to current spawn.
	 * 
	 * @throws IOException
	 */
	public void sendEnter() throws IOException{
		mCurrentExpectObj.send("\r\n");
	}
	
	/**
	 * Send Ctrl+C to current spawn.
	 * 
	 * @throws IOException
	 */
	public void sendCtrlC() throws IOException{
		mCurrentExpectObj.send("\003");
	}
	
	/**
	 * Send a string to current spawn.
	 * 
	 * @param aStr
	 * @throws Exception
	 */
	public void sendString(String aStr) throws Exception {
		if( aStr != null && !aStr.isEmpty() )
			mCurrentExpectObj.send(aStr);
	}
	
	/**
	 * Send a command to current spawn.
	 * 
	 * @param aStr
	 * @throws Exception
	 */
	public void sendCommand(String aStr) throws Exception {
		sendString(aStr);
		sendEnter();
	}
	
	/**
	 * Set expect timeout.
	 * 
	 * @param aTimeout
	 */
	public void setExpectTimeout(int aTimeout){
		mCurrentExpectObj.setDefaultTimeout(aTimeout*1000);
	}
	
	/**
	 * Reset the expect timeout.
	 * 
	 */
	public void resetExpectTimeout(){
		mCurrentExpectObj.setDefaultTimeout(Expect4j.TIMEOUT_DEFAULT);
	}
	
	/**
	 * Check a string from expect.
	 * 
	 * @param aString
	 * @return
	 * @throws Exception
	 */
	public boolean expectString(String aString) throws Exception {
		return mCurrentExpectObj.expect(aString) > -1;
	}
	
	
	/**
	 * Assert a string from expect.
	 * 
	 * @param aString
	 * @return
	 * @throws Exception
	 */
	public String expectShouldContain(String aString) throws Exception {
		return expectString(aString) ? "PASS" : "FAIL";
	}
	
	/**
	 * Switch current spawn with aSpwanNum.
	 * 
	 * @param aSpwanNum
	 */
	public void switchSpawn(long aSpwanNum){
		if( !mExpectObjectMap.containsKey(aSpwanNum)){
			throw new RuntimeException("Invalid spawn number: " + aSpwanNum);
		}
		
		mCurrentExpectObj = mExpectObjectMap.get(aSpwanNum);
	}
}
