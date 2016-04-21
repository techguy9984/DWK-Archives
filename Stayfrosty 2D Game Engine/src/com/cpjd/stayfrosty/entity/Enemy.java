package com.cpjd.stayfrosty.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.Tile;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Enemy extends Sprite {

	protected double health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage; // How much damage they do on contact
	
	protected int projRange; // Range that firing works
	
	protected boolean flinching;
	protected long flinchTimer;
	
	protected Random r;
	
	// Firing
	protected boolean notFire; // Set to true if the enemy does not fire
	
	public Enemy(TileMap tm) {
		super(tm);
		
		r = new Random();
	}
	
	public boolean isDead() {
		return dead;
	}
	
	protected double calculateDifference(double x1, double y1, double x2, double y2) {
		return Math.hypot(Math.abs(x2 - x1), Math.abs(y2 - y1));
	}
	
	
	public int getDamage() {
		return damage;
	}
	
	public void checkFatalBlocks() {
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;

		if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		int tl = tileMap.getType(topTile, leftTile); // Types of the tiles around us
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		if(tl == Tile.FATAL || tr == Tile.FATAL || bl == Tile.FATAL || br == Tile.FATAL) {
			hitingFatalBlock = true;
		} else {
			hitingFatalBlock = false;
		}
	}
	
	/* enemy gets hit with x damage */
	public void hit(double damage) {
		if(dead) return;
		if(flinching) return;
		
		health -= damage;
		if(health < 0 ) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void addHealth(int amount) {
		health += amount;
		if(health > maxHealth) health = maxHealth;
	}
	
	public void update() {}

	public void draw(Graphics2D g) {

		// Buffered image automatically handles not on screen
		setMapPosition();
	
		if (GamePanel.DEBUG) {
			g.setColor(Color.RED);
			//g.drawLine((int) (getx() + xmap), (int) (gety() + ymap), (int) (Player.globalX + xmap),
				//	(int) (Player.globalY + ymap));
		}
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
	
}
