package com.cpjd.stayfrosty.attacks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Sprite;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Cube extends Sprite {
	BufferedImage cube;
	
	private boolean hit;
	private boolean remove;
	
	private boolean facingRight;
	
	private long startTime;
	private long elapsed;
	
	// Ranges
	private int xdist;
	private int ydist;
	private int degrees;
	private int degInc;
	private int pulse;
	private int pulseInc;
	
	public Cube(TileMap tm, boolean right) {
		super(tm);
		
		facingRight = right;
		
		startTime = System.nanoTime();
		pulseInc = 3;
		moveSpeed = 5.0;
		if(right) dx = moveSpeed;
		else dx =- moveSpeed;
		
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
	
	// If the bullet hits something
	public void setHit() {
		if(hit) return;
		hit = true;
		dx = 0;
	}
	
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		// v = initial velocity + g x t
		elapsed = (System.nanoTime() - startTime) / 1000000000;
		
		dy += 1.8 * (-0.04+(0.1*elapsed));
		
		xdist += dx;
		ydist += dy;
		
		if(xdist > 400) dx = 0;
		if(ydist > 50) {
			dy = 0;
			degInc++;
			if(degInc > 30) degInc = 2;
			degrees+=degInc;
			if(degrees > 360) degrees = 0;
			
			pulse+=pulseInc;
			if(pulse > 200 || pulse < 0) pulseInc = -pulseInc;
			
		}
		
		if(hit) {
			remove = true;
		}
	}
	public void draw(Graphics2D g) {
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degrees), cube.getWidth() / 2, cube.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		setMapPosition();
		
		// Enable antialiasing for this - oval effects
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.GREEN);
		g.setStroke(new BasicStroke(8));
		if(dy == 0) {
			g.drawOval((int) (x + xmap - width / 2) - pulse / 2 + 12, (int) (y + ymap - height / 2) - pulse / 2 + 11, pulse, pulse);
		}
		g.setStroke(new BasicStroke(1));
		
		if (facingRight) {
			g.drawImage(op.filter(cube, null), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		} else {
			g.drawImage(op.filter(cube, null), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
					-op.filter(cube, null).getWidth(), op.filter(cube, null).getHeight(), null);
		}

	}
	
}
