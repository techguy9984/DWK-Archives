package com.cpjd.stayfrosty.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.shop.Stats;
import com.cpjd.stayfrosty.tilemap.Tile;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.MultiBullet;

public class Enemy extends Sprite {

	protected ArrayList<Enemy> babySpoders;
	
	protected double health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage; // How much damage they do on contact
	
	protected int projRange; // Range that firing works
	
	protected boolean flinching;
	protected long flinchTimer;
	
	protected Random r;
	
	// Probs
	protected int memeProb;
	protected int doritoeProb;
	protected int healthProb;
	
	// How much is stolen at once
	protected int memeAmount = 1;
	protected int doritoeAmount = 1;
	protected int healthAmount = 1;
	
	// Total inventory
	public int totalMemes;
	private int totalDoritoes;
	
	// Temp values for removing player stuff
	private int sMemes;
	private int sDoritoes;
	private int sHealth;
	
	protected ArrayList<MultiBullet> mbullets;
	
	// Firing
	protected boolean notFire; // Set to true if the enemy does not fire
	
	// Enemy inventories
	protected int memes;
	protected int doritoes;
	
	public Enemy(TileMap tm) {
		super(tm);
		
		r = new Random();
	}
	
	public boolean isDead() {
		if(dead == true) Stats.enemiesKilled++;
		return dead;
	}
	
	public ArrayList<MultiBullet> getBulletArray() {
		return mbullets;
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
		Stats.hit++;
		if(flinching) return;
		
		health -= damage;
		if(health < 0 ) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void addHealth(int amount) {
		sHealth = amount;
		health += amount;
		if(health > maxHealth) health = maxHealth;
	}
	
	public void update() {}
	
	// For baby spoders
	public ArrayList<Enemy> getBabies() {
		return babySpoders;
	}
	
	// Run probabilites
	public void runProbs() {
		if(r.nextInt(100) < memeProb) addMemes(memeAmount);
		if(r.nextInt(100) < doritoeProb) addDoritoes(doritoeAmount);
		if(r.nextInt(100) < healthProb) addHealth(healthAmount);
	}
	
	// To steal from player
	public int getStolenHealth() {
		int temp = sHealth;
		sHealth = 0;
		return temp;
	}
	
	public int getStolenMemes() {
		int temp = sMemes;
		sMemes = 0;
		return temp;
	}
	
	public int getStolenDoritoes() {
		int temp = sDoritoes;
		sDoritoes = 0;
		return temp;
	}
	
	
	
	// Stealing
	public void addMemes(int amount) {
		if(Player.currentMemes <= 0) return;
		totalMemes += amount;
		sMemes = amount;
		memes += amount;
	}
	
	public int getMemes() {
		return memes;
	}
	
	public int getTotalMemes() {
		return totalMemes;
	}
	
	public int getTotalDoritoes() {
		return totalDoritoes;
	}
	
	public void addDoritoes(int amount) {
		if(Player.currentDoritoes <= 0) return;
		totalDoritoes += amount;
		sDoritoes = amount;
		doritoes += amount;
	}
	
	public int getDoritoes() {
		return doritoes;
	}
	
	public void draw(Graphics2D g) {

		// Buffered image automatically handles not on screen
		setMapPosition();

		if(!notFire) {
			for (int i = 0; i < mbullets.size(); i++) {
				mbullets.get(i).draw(g);
			}
		}
			
		if (GamePanel.debug) {
			g.setColor(Color.RED);
			g.drawLine((int) (getx() + xmap), (int) (gety() + ymap), (int) (Player.globalX + xmap),
					(int) (Player.globalY + ymap));
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