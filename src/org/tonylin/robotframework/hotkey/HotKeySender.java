package org.tonylin.robotframework.hotkey;

public class HotKeySender {

	private static String[] getCtrlA(){
	     String [] keys = {
	                "VK_CONTROL", "VK_A"
	     };
	     return keys;
	}
	
	private static String[] getAltF9(){
		   String [] keys = {
	                "VK_ALT", "VK_F9"
	     };
	     return keys;
	}
	
	private static String[] takeSubStrings(String []aStrings){
		String[] keys = new String[aStrings.length-1];
		for( int i = 1 ; i < aStrings.length ; i++ ){
			keys[i-1] = aStrings[i];
		}
		return keys;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if( args == null || args.length == 0 ){
			System.out.println("Please enter valid keys.");
			System.exit(1);
		}
		
		try {
			Thread.sleep(500);
			
			if( args[0].equalsIgnoreCase("ctrl-a") ){
				HotKeyUtil.sendKeysCombo(getCtrlA());	
			} else if( args[0].equals("-press") ){
				HotKeyUtil.pressKey(takeSubStrings(args));
			} else if( args[0].equals("-release") ){
				HotKeyUtil.releaseKey(takeSubStrings(args));
			} else if( args[0].equals("-plm")) { 
				HotKeyUtil.pressLeftMouse(Integer.valueOf(args[1]),
						Integer.valueOf(args[2]));
			} else if( args[0].equals("alt-f9")){
				HotKeyUtil.sendKeysCombo(getAltF9());
			} else {
				HotKeyUtil.sendKeysCombo(args);
			}
			
			
			System.exit(0);
		} catch( Exception e ){
			System.out.println("Send key failed: " + e.getMessage());
			System.exit(2);
		}
	}
}
