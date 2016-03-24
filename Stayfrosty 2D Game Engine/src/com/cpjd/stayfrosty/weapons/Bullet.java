package com.cpjd.stayfrosty.weapons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Sprite;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Bullet extends Sprite {
	BufferedImage bullet;
	
	private boolean hit;
	private boolean remove;
	
	//private int damage;
	private double accuracy;
	
	private boolean facingRight;
	
	public Bullet(TileMap tm, boolean right,int damage, double accuracy) {
		super(tm);
		
		this.accuracy = accuracy;
		
		facingRight = right;
		
		//this.damage = damage;
		
		moveSpeed = 15.0;
		if(right) dx = moveSpeed;
		else dx =- moveSpeed;
		
		// Drawing
		width = 10;
		height = 3;
		
		// Actual widths
		cwidth = 14;
		cheight = 14;
		
		// sprites
		try {
			bullet = ImageIO.read(getClass().getResourceAsStream("/Weapons/bullet.png"));
		} catch(IOException e) {
			
		}
	}
	
	public void setImagePath(String path) {
		try {
			bullet = ImageIO.read(getClass().getResourceAsStream("/Weapons/"+path));
		} catch(IOException e) {
			
		}
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	// If the bullet hits something
	public void setHit() {
		if(hit) return;
		hit = true;
		dx = 0;
	}
	
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp,ytemp + accuracy);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		if(hit) {
			remove = true;
		}
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		if (facingRight) {
			g.drawImage(bullet, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		} else {
			g.drawImage(bullet, (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
					-width, height, null);
		}
	}
	
}
