package org.tonylin.robotframework.sikuli;

import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class SikuliLibrary {
	public static final String ROBOT_LIBRARY_VERSION = "1.0";
	public static final String ROBOT_LIBRARY_SCOPE = "TEST SUITE";
	
	private Mouse mMouse = new DesktopMouse();
	private Keyboard mKeyboard = new DesktopKeyboard();
	private ScreenRegion mDesktopSR = new DesktopScreenRegion();
	private boolean mIsDebugEnable = false;
	private int mTimout = 5000;
	
	public SikuliLibrary(){
		
	}
	
	/**
	 * Enable debug mode to mark the found images.
	 */
	public void enableDebugMode(){
		mIsDebugEnable = true;
	}

	/**
	 * Disable debug mode.
	 */
	public void disableDebugMode(){
		mIsDebugEnable = false;
	}
	
	public void setTimeout(int aTimeout){
		mTimout = aTimeout;
	}
	
	private void showCanvas(ScreenRegion aSR){
		if( aSR != null ) {
			Canvas canvas = new DesktopCanvas();
			canvas.addBox(aSR).display(3);
		}
	}
	
	private void showDebugCanvas(ScreenRegion aSR){
		if( mIsDebugEnable && aSR != null ){
			showCanvas(aSR);
		}
	}
	
	private ScreenRegion getScreenRegion(String aImagePath){
		Target target = new ImageTarget(new File(aImagePath));
		ScreenRegion sr = mDesktopSR.wait(target, mTimout);
		showDebugCanvas(sr);
		return sr;
	}
	
	private ScreenRegion getScreenRegionEx(String aImagePath) throws SikuliLibraryException{
		Target target = new ImageTarget(new File(aImagePath));
		ScreenRegion sr = mDesktopSR.wait(target, mTimout);
		if( sr == null ){
			throw new SikuliLibraryException("Image dones't exist: " + aImagePath);
		}
		showDebugCanvas(sr);
		return sr;
	}
	
	/**
	 * Click the image on screen.
	 * 
	 * @param aImagePath
	 * @throws SikuliLibraryException
	 */
	public void clickImage(String aImagePath) throws SikuliLibraryException{
		ScreenRegion sr = getScreenRegionEx(aImagePath);
		mMouse.click(sr.getCenter());
	}

	/**
	 * Verify a image should be on screen.
	 * 
	 * @param aImagePath
	 * @throws SikuliLibraryException
	 */
	public void ImageShouldExist(String aImagePath) throws SikuliLibraryException{
		if( findImage(aImagePath) == null ){
			throw new SikuliLibraryException("Image dones't exist: " + aImagePath);
		}
	}
	
	/**
	 * Find the image location. If it finds 
	 * the image, it will return the location [x,y].
	 * Otherwise, it will return null.
	 * 
	 * @param aImagePath
	 * @return
	 */
	public int[] findImage(String aImagePath){
		ScreenRegion sr = getScreenRegion(aImagePath);
		if( sr == null ){
			return null;
		}
		return new int[]{ sr.getCenter().getX(), sr.getCenter().getY()};
	}
	
	/**
	 * Find all target images locations. The result will return a array.
	 * The index 0 is the target numbers. For example, [ 2, 100, 100, 150, 150 ]
	 * present that two target images and the locations are [100,100] and [150,150].
	 * 
	 * @param aImagePath
	 * @return
	 */
	public int[] findAllImage(String aImagePath){
		Target target = new ImageTarget(new File(aImagePath));
		List<ScreenRegion>  srList = mDesktopSR.findAll(target);
		
		int dataSize = 1 + srList.size()*2;
		int[] result = new int[ dataSize ];
		result[0] = srList.size();
		Canvas canvas = new DesktopCanvas();
		for( int i = 0 ; i <  srList.size() ; i++ ){
			ScreenRegion sr = srList.get(i);
			
			result[(2*i)+1] = sr.getCenter().getX();
			result[(2*i)+2] = sr.getCenter().getY();
			
			canvas.addBox(sr);
		}

		if( mIsDebugEnable ){
			canvas.display(3);
		}
		return result;
	}
	
	/**
	 * Drag and drop a image to a target image.
	 * 
	 * @param aImagePath
	 * @param aTargetImagePath
	 * @throws SikuliLibraryException 
	 */
	public void moveImage(String aImagePath, String aTargetImagePath) throws SikuliLibraryException{
		ScreenRegion sourceSR = getScreenRegionEx(aImagePath);
		ScreenRegion targetSR = getScreenRegionEx(aTargetImagePath);
		
		mMouse.drag(sourceSR.getCenter());
		mMouse.drop(targetSR.getCenter());
	}
	
	/**
	 * Move the mouse location to (aX,aY)
	 * 
	 * @param aX
	 * @param aY
	 */
	public void moveMouseTo(int aX, int aY){
		mMouse.drop(new DefaultScreenLocation(mDesktopSR.getScreen(), aX, aY));
	}
	
	/**
	 * Get the mouse location.
	 * 
	 * @return a location array for index(0,1) = l(x,y)
	 */
	public int[] getMouseLocation(){
		ScreenLocation sl = mMouse.getLocation();
		int[] location = new int[2];
		location[0] = sl.getX();
		location[1] = sl.getY();
		
		return location;
	}
	
	/**
	 * Type string.
	 * 
	 * @param aStr
	 */
	public void typeString(String aStr){
		mKeyboard.type(aStr);
	}
	
	/**
	 * Send combo keys. Please find keys string in 
	 * http://docs.oracle.com/javase/6/docs/api/java/awt/event/KeyEvent.html.
	 * 
	 * @param aKeys
	 * @throws SikuliLibraryException
	 */
	public void sendComboKeys(String[] aKeys) throws SikuliLibraryException{
		Class<?> cl = KeyEvent.class;

		int[] intKeys = new int[aKeys.length];
		try {
			for (int i = 0; i < aKeys.length; i++) {
				Field field = cl.getDeclaredField(aKeys[i]);
				intKeys[i] = field.getInt(field);
				mKeyboard.keyDown(intKeys[i]);
			}
			
			for (int i = aKeys.length - 1; i >= 0; i--)
				mKeyboard.keyUp(intKeys[i]);
		} catch (Exception e) {
			throw new SikuliLibraryException("Send combo keys failed: " + e.getMessage());
		}
	}
}
