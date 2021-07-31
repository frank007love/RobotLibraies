package org.tonylin.robotframework.sikuli;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class TestSikuliAPI {

	static private String TEST_FOLDER = "./testdata/sikuli/"; 
	
	private File getTestFile(String aFileName){
		return new File(TEST_FOLDER + aFileName);
	}
	
	private URL getTestFileURL(String aFileName) throws Throwable{
		return getTestFile(aFileName).toURI().toURL();
	}
	
	@Test
	public void tryCanvus(){
		ScreenRegion s = new DesktopScreenRegion();
		Target target = new ImageTarget(getTestFile("eclipseIcon.png"));
		ScreenRegion r = s.find(target);
		
		Canvas canvas = new DesktopCanvas();
		canvas.addBox(r).display(3);
	}
	
	@Test
	public void tryColorImageTarget() throws Throwable {
		URL url = new URL("http://wiki.sikuli-api.googlecode.com/git/images/colorImageTarget/redBox.png");
		ScreenRegion s = new DesktopScreenRegion();
		//Target target = new ImageTarget(url);
		Target target = new ColorImageTarget(url);
		Mouse mouse = new DesktopMouse();
		for( ScreenRegion r : s.findAll(target)){
			System.out.println(r.getBounds());
			mouse.drop(r.getCenter());
		}
	}
	
	@Test
	public void searchGameRegion() throws Throwable{
		ScreenRegion s = new DesktopScreenRegion();
		Target target = new ColorImageTarget(getTestFileURL("closeIcon.png"));
		ScreenRegion r = s.find(target);
		System.out.println(r.getCenter().getX());
		System.out.println(r.getCenter().getY());
		Mouse mouse = new DesktopMouse();
		mouse.drop(r.getCenter());
	}
	
	@Test
	public void changeSize() throws IOException{
		File imageFile = new File("testdata/1366_reload.png");
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		
		double[] orgResolution = { 1366.0, 768.0};
		double[] tarResolution = { 1024.0, 768.0};
		double[] ratio = { orgResolution[0]/tarResolution[0],
				orgResolution[1]/tarResolution[1]};
		
		
		Image image = bufferedImage.getScaledInstance((int)(bufferedImage.getWidth()/ratio[0])
				, (int)(bufferedImage.getHeight()/ratio[1])
				, Image.SCALE_SMOOTH);
		
		BufferedImage tag = new BufferedImage((int)(bufferedImage.getWidth()/ratio[0]), 
				(int)(bufferedImage.getHeight()/ratio[1]), BufferedImage.TYPE_INT_RGB);
		Graphics graph = tag.getGraphics();
		graph.drawImage(image, 0, 0, null);
		graph.dispose();
		ImageIO.write(tag, "png", new File("testdata/1024_reload_tmp.png"));
	}
}
