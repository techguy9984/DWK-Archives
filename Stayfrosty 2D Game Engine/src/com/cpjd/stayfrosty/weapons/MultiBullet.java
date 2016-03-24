package com.cpjd.stayfrosty.weapons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Sprite;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class MultiBullet extends Sprite {
	BufferedImage bullet;
	
	private boolean hit;
	private boolean remove;
	
	// Enemy location
	private double x1;
	private double y1;
	
	// Player location
	private double x2;
	private double y2;
	
	// Equation of the line connecting these two points
	private double slope;
	private double intercept;
	
	public MultiBullet(TileMap tm, int sx1, int sy1, int sx2, int sy2, boolean fireDown) {
		super(tm);
	
		// Prevent horizontal lines
		if(sy1 == sy2) sy2++;
		
		// Set the values
		this.x1 = sx1;
		this.y1 = sy1;
		this.x2 = sx2;
		this.y2 = sy2;
		
		slope = (y2 - y1) / (x2 - x1);
		intercept = (y1 - (slope * x1));
		
		// Get a new point
		double nexty = getEY(x1 + 14);
		double nextx = x1 + 14;

		if (nexty == 0) {
			nexty = 1;
		}
		dx = Math.abs(nextx - x1);
		dy = Math.abs(nexty - y1);
		
		if(x2 < x1) dx = -dx;
		if(y2 < y1) dy = -dy;
		
		if(!fireDown) if(y2 > y1 + 10) dy = 0; // Don't allow the illuminati to fire downwards
		
		// Drawing
		width = 10;
		height = 10;
		
		// Actual widths
		cwidth = 10;
		cheight = 10;
		
		// sprites
		try {
			bullet = ImageIO.read(getClass().getResourceAsStream("/Weapons/enemy.png"));
		} catch(IOException e) {
			
		}
	}

	
	public void setImage(String path) {
		try {
			bullet = ImageIO.read(getClass().getResourceAsStream("/Weapons/baby.png"));
		} catch(IOException e) {}
		cwidth = bullet.getWidth();
		cheight = bullet.getHeight();
		width = bullet.getWidth();
		height = bullet.getHeight();
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	// Takes x and returns y
	private double getEY(double xc) {
		return (xc * slope) + intercept;
	}
	
	// If the bullet hits something
	public void setHit() {
		if(hit) return;
		hit = true;
		dx = 0;
		dy = 0;
	}
	
	
	int counter = 0;
	
	public void update() {
		checkTileMapCollision();
			
		setPosition(xtemp, ytemp);
		
		if(dx == 0 || dy == 0 && !hit) {
			setHit();
		}
		
		if(hit) {
			remove = true;
		}
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(bullet, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
	}
	
}
