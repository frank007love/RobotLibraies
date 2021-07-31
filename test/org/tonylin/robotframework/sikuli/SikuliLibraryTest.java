package org.tonylin.robotframework.sikuli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SikuliLibraryTest {
	private SikuliLibrary mSikuliLibrary = new SikuliLibrary();
	@BeforeClass
	public static void classSetup() throws Exception {
		System.setProperty("java.library.path", "./libs/opencv");
		
		
		final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
	    sysPathsField.setAccessible(true);
	    sysPathsField.set(null, null);
	}
	
	@After
	public void tearDown(){
		mSikuliLibrary.disableDebugMode();
	}
	
	@Test
	public void moveMouseTo(){
		int x = 100, y = 200;
		mSikuliLibrary.moveMouseTo(x, y);
		int[] location = mSikuliLibrary.getMouseLocation();
		assertEquals(x, location[0]);
		assertEquals(y, location[1]);
	}
	
	@Test
	public void findImage_windows(){
		int[] loc =  mSikuliLibrary.findImage("./testdata/sikuli/eclipseIcon.png");
		assertNotNull(loc);
		System.out.println("x: " + loc[0] + ", y: " + loc[1]);
		
		///root/workspace/RobotLibraies/examples/testdata/sikuli/test.png
	}
	
	@Test
	public void findImage_linux(){
		mSikuliLibrary.enableDebugMode();
		int[] loc =  mSikuliLibrary.findImage("./testdata/sikuli/ubuntu_javafile.png");
		assertNotNull(loc);
		System.out.println("x: " + loc[0] + ", y: " + loc[1]);
	}
	
	@Test
	public void moveImage() throws SikuliLibraryException, InterruptedException, IOException{
		mSikuliLibrary.sendComboKeys(new String[]{
				"VK_WINDOWS", "VK_D",
		});
		Thread.sleep(500);
		
		File file = new File(System.getenv("USERPROFILE")+"/Desktop/test.txt");
		file.createNewFile();
		Thread.sleep(500);
		
		mSikuliLibrary.moveImage("./testdata/sikuli/textfile.png",
				"./testdata/sikuli/dustbin.png");
	}
	
	@Test
	public void sendComboKeys() throws SikuliLibraryException, InterruptedException{
		mSikuliLibrary.sendComboKeys(new String[]{
				"VK_WINDOWS", "VK_D",
		});
		Thread.sleep(1000);
		mSikuliLibrary.sendComboKeys(new String[]{
				"VK_WINDOWS", "VK_D"
		});
	}
	
	@Test
	public void enableDebugMode(){
		mSikuliLibrary.enableDebugMode();
		int[] loc =  mSikuliLibrary.findImage("./testdata/sikuli/eclipseIcon.png");
		assertNotNull(loc);
		System.out.println("x: " + loc[0] + ", y: " + loc[1]);
	}
	
	@Test
	public void findAll() throws Exception{
		mSikuliLibrary.sendComboKeys(new String[]{
				"VK_WINDOWS", "VK_D",
		});
		Thread.sleep(1000);
		mSikuliLibrary.enableDebugMode();
		assertNotNull(mSikuliLibrary.findAllImage("./testdata/sikuli/textfileIcon.png"));	
		
		mSikuliLibrary.sendComboKeys(new String[]{
				"VK_WINDOWS", "VK_D",
		});
	}
	
	@Test
	public void findAll_NotExist(){
		mSikuliLibrary.enableDebugMode();
		int []result = mSikuliLibrary.findAllImage("./testdata/sikuli/textfileIcon.png");
		assertEquals(1, result.length);
		assertEquals(0, result[0]);
	}
}
