package com.cpjd.stayfrosty.menu;

import java.awt.Graphics2D;

public class Slider {
	
	// The slider
	private int x;
	private int y;
	private int width;
	
	// The button
	private int count;
	private final int size = 40; // Diameter of the button
	
	// Min and max
	private int min;
	private int max;
	private int ratio; // Ratio of pxs to counts
	
	public Slider(int x, int y, int width) {
		this.width = width;
		this.x = x;
		this.y = y;
		
		min = x;
		max = x + width;
	}
	
	public void update() {
		
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(GameStateManager.hud);
		g.fillRect(x,y,width,40);
		
		
	}
	
}
