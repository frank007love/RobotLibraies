package org.tonylin.robotframework.hotkey;

import java.lang.reflect.*;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class HotKeyUtil {

	/**
	 * Mouse left down on aX,aY location.
	 * 
	 * @param aX
	 * @param aY
	 * @throws Exception
	 */
	public static void pressLeftMouse(int aX, int aY) throws Exception {
		Robot robot = new Robot();
		robot.mouseMove(aX, aY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	/**
	 * Send keys press event. Please pass KeyEvent String(s) like VK_ENTER.
	 * 
	 * @param aKeys
	 * @throws Exception
	 */
	public static void pressKey(String aKeys[]) throws Exception {
		Robot robot = new Robot();

		Class<?> cl = KeyEvent.class;

		int[] intKeys = new int[aKeys.length];

		for (int i = 0; i < aKeys.length; i++) {
			Field field = cl.getDeclaredField(aKeys[i]);
			intKeys[i] = field.getInt(field);
			robot.keyPress(intKeys[i]);
		}
	}

	/**
	 * Send keys release event. Please pass KeyEvent String(s) like VK_ENTER.
	 * 
	 * @param aKeys
	 * @throws Exception
	 */
	public static void releaseKey(String aKeys[]) throws Exception {
		Robot robot = new Robot();

		Class<?> cl = KeyEvent.class;

		int[] intKeys = new int[aKeys.length];

		for (int i = aKeys.length - 1; i >= 0; i--) {
			Field field = cl.getDeclaredField(aKeys[i]);
			intKeys[i] = field.getInt(field);
			robot.keyRelease(intKeys[i]);
		}
	}

	/**
	 * Send keys press&release event. Please pass KeyEvent String(s) like VK_ENTER.
	 * 
	 * @param aKeys
	 * @throws Exception
	 */
	public static void sendKeysCombo(String aKeys[]) throws Exception {
		Robot robot = new Robot();

		Class<?> cl = KeyEvent.class;

		int[] intKeys = new int[aKeys.length];

		for (int i = 0; i < aKeys.length; i++) {
			Field field = cl.getDeclaredField(aKeys[i]);
			intKeys[i] = field.getInt(field);
			robot.keyPress(intKeys[i]);
		}

		for (int i = aKeys.length - 1; i >= 0; i--)
			robot.keyRelease(intKeys[i]);
	}

}
