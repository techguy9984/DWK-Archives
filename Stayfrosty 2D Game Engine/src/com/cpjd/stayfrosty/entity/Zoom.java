package com.cpjd.stayfrosty.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Zoom {

	// Collected items have a nice animatinon effect
	
	// Images
	BufferedImage image;
	// Locations
	private double x;
	private double y;
	
	// Pyshics
	private double currentSpeed;
	private double maxSpeed;
	private double increment; // The current value being added
	private double increase; // The amount added to the increment each update
	
	// Markers
	private boolean finished;
	
	public Zoom(String path, int x, int y) {
		
		increase = 0.05;
		maxSpeed = 15;
		
		this.x = x;
		this.y = y;
		
		reset();
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch(Exception e) {
			
		}
		
	}
	
	
	public void update() {
		currentSpeed += increment;
		increment += increase;
		if(currentSpeed > maxSpeed) currentSpeed = maxSpeed;
		x -= currentSpeed;
		y -= currentSpeed * 0.3;
		
		if(x < 0 && y < 0) {
			finished = true;
		}
	}
	
	
	private void reset() {
		increment = 0.05;
		currentSpeed = 0;
		finished = false;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
		
	}
	
	
}
