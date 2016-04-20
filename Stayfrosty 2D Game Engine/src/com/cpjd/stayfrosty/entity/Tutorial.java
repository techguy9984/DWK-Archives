package com.cpjd.stayfrosty.entity;

import java.awt.Graphics2D;

public class Tutorial {
	
	// MOTD
	public String[] motd = {
			"16,851 lines of code","Amazing animations","Developed in 2 months","Written in Java"
	};
	
	
	public static final String jump = "Hold the space bar longer to jump higher!";
	public static final String police = "Oh no! You lost all your powerups!";
	public static final String Penguin = "Club Penguins are extremely weak before they hatch, but'll give you a run for your money if they do. ";
	public static final String Troller = "The troller will relentelessy follow you until it crushes you, don't let it touch you!";
	public static final String Skeleton = "You've found a skeleton key! Press G to toggle blocks.";
	
	private String display;
	private int x;
	private int y;
	private int xrange;
	private int yrange;
	private int time;
	
	private int drawx;
	private int drawy;
	
	private boolean active; // If we are drawing the text to the screen
	
	public Tutorial(String display, int x, int y, int xrange, int yrange, int time, int drawx, int drawy) {
		this.display = display;
		this.x = x;
		this.y = y;
		this.xrange = xrange;
		this.yrange = yrange;
		this.time = time;
		this.drawx = drawx;
		this.drawy = drawy;
	}
	
	public Tutorial() {}
	
	public String getMOTD(int index) {
		return motd[index];
	}
	
	public void update() {
		/* Set active if the player is in range
		if(Player.globalX > x && Player.globalX < x + xrange && Player.globalY > y && Player.globalY < y + yrange) {
			active = true;
			
			// Update the time
			time--;
			if(time <= 0) {
				time = 0;
				active = false;
				return;
			}
		} else {
			active = false;
		}*/
	}
	
	public void draw(Graphics2D g) {
		if(!active) return;

	}
	
}
