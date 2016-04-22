package com.cpjd.stayfrosty.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Log;

/* Description
 *  Scrolls the background, supports parallax
 *  Backgrounds should be the same size as the unchanged GamePanel.WIDTH & GamePanel.HEIGHT values
 */
public class Background {
	
	private BufferedImage image;
	
	private int width;
	
	// Background one
	private double x; // Location of background image
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale; // The scale at which the background moves, right 10, scale 0.1 (10% regular speed) >> parallax effect
	
	public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s)); // How to import files into the program
			width = image.getWidth();
			moveScale = ms;
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
	}
	
	public void setPosition(double x, double y) {
		// Resets if the image goes off screen, smooth scrolling effect
		this.x = (x * moveScale) % GamePanel.WIDTH; 
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	public void setVector(double dx, double dy) { // Auto-matic scrolling
		this.dx = dx;
		this.dy = dy;
		
	}
	public void update() {
		x += dx;
		y += dy;
		
		//if(x < 0 - width) x = GamePanel.WIDTH;
		//if(x > GamePanel.WIDTH) x = 0;
		if(x < 0 - width) x = width;
		if(x > width) x = 0;
	}
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) x, (int) y, null);

		// Have to draw new instance if the background goes off-screen
		if (x < 0) g.drawImage(image, (int) x + width, (int) y, null);
		if (x > 0) g.drawImage(image, (int) x - width, (int) y, null);
	}
	public BufferedImage getSub(int x, int y, int width, int height) {
		BufferedImage sub = image.getSubimage(x, y, width, height);
		return sub;
	}
	public BufferedImage getImage() {
		return image;
	}
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
}
