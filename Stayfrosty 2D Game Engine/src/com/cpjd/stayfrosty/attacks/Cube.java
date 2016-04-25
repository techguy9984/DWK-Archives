package com.cpjd.stayfrosty.attacks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Sprite;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Cube extends Sprite {
	// Identity
	BufferedImage cube;
	private boolean remove;
	private boolean facingRight;
	
	// Constants
	private double GRAVITY = 0.3; // px negative is up computer, life = ngative is down
	private final double POWER = -9;
	
	// Variables
	private long startTime;
	private double elapsedTicks;
	private double distance; // Distance between player and target
	private double time; // Precalculated time for cube to reach ty (while falling)
	private double py;
	private double px;
	
	public Cube(TileMap tm, boolean right, double px, double py, double tx, double ty) {
		super(tm);
		
		this.right = right;
		
		setPosition(px, py);
		startTime = System.nanoTime();
		
		this.py = py; this.px = px;
		
		// 1 - calc distance between player and target, in pixels
		distance = calculateDifference(px,py,tx,ty);
		
		// 2 - solve for t using the quadratic formula
		// a = -GRAVITY
		double a = 0.5 * GRAVITY;
		double b = POWER;
		double c = py - ty;
		time = (-b + Math.sqrt((b * b) + (- 4 * a * c))) / (2 * a);
		
		dx = distance / (time * GamePanel.FPS);
		System.out.println(dx+"yes");
		facingRight = right;
		

		// Drawing
		width = 25;
		height = 25;
		
		// Actual widths
		cwidth = 25;
		cheight = 25;
		
		// sprites
		try {
			cube = ImageIO.read(getClass().getResourceAsStream("/Weapons/rubiks.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void setPosition(double x, double y) {
		super.setPosition(x, y);
	}
	
	public void update() {

		elapsedTicks++;
		if(right) px += 6;
		if(!right) px -= 6;
		xtemp = px;
		ytemp = -(-0.5 * GRAVITY * (elapsedTicks * elapsedTicks)) + (POWER * elapsedTicks) + (py);

		//System.out.println(ytemp);
		//xxdcheckTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//System.out.println(getx()+":"+gety());
	}
	public void draw(Graphics2D g) {
		setMapPosition();

		// Draw the cube
		if (facingRight) {
			g.drawImage(cube, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		} else {
			g.drawImage(cube, (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
					-cube.getWidth(), cube.getHeight(), null);
		}

	}
	// Calculates the distance between two points
	protected double calculateDifference(double x1, double y1, double x2, double y2) {
		return Math.hypot(Math.abs(x2 - x1), Math.abs(y2 - y1));
	}
}
