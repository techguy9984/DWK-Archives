package com.cpjd.stayfrosty.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Player extends Sprite {

	// Life
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected boolean flinching;
	protected long flinchTimer;
	
	// Movement
	protected double walkSpeed;
	
	// Animations
	protected ArrayList<BufferedImage[]> sprites;
	
	public Player(TileMap tm) {
		super(tm);
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void hit(int damage) {
		if(GamePanel.DEBUG) return;
		if(flinching) return;
		
		AudioPlayer.playSound(SKeys.Damage);
		
		health -= damage;
		
		if(health < 0) health = 0;
		if(health == 0) {
			dead = true;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	protected void getNextPosition() {
		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}
		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}

		// falling
		if (falling) {
			dy += fallSpeed;

			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping) dy += stopJumpSpeed;

			if (dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	
	protected void checkTileCollision() {
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;

		if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			hit(100);
			return;
		}
	}
	public void draw(Graphics2D g) {

		setMapPosition();

		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
	
}
