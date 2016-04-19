package com.cpjd.stayfrosty.players;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Sprite;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;

/* Daniel
 * -Track runner and hobbies enthusiast
 * 
 * Abilities
 * -Can move faster than other characters and has more agility for jumping, etc. double jump
 * -Rubik's cube attack or defense or something
 * -Juggle grenades and throw them at targets
 * -Projectile attack
 * 
 */
public class Daniel extends Sprite {
	
	// Life
	private int health;
	private int maxHealth;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	// Movement
	private double walkSpeed;
	
	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUM_FRAMES = {2,8,1,2,2};
	
	// Animation action ids
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int RUBIKS = 4;
	private static final int JUGGLE = 5;
	
	// Technical
	private GameStateManager gsm;
	
	public Daniel(TileMap tm, GameStateManager gsm) {
		super(tm);
		
		this.gsm = gsm;
		
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 64;
		
		walkSpeed = 3.3;
		maxSpeed = 3.3;
		moveSpeed = walkSpeed;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 7.0;
		jumpStart = -7;
		stopJumpSpeed = 0.3;
		facingRight = true;
		
		health = maxHealth = 100;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/bryan.gif"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 5; i++) {

				BufferedImage[] bi = new BufferedImage[NUM_FRAMES[i]];

				for (int j = 0; j < NUM_FRAMES[i]; j++) {

					if (i != 6) {
						bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					} else {
						bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
					}

				}

				sprites.add(bi);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
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
	private void getNextPosition() {
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
	private void checkTileCollision() {
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
	
	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		checkTileCollision();
		
		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) { // 1 second of inviciblity
				flinching = false;
			}
		}

		// set animation
		if (dy > 0) {
			if (currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 64;
			}
		} else if (dy < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 64;
			}
		} else if (left || right) {
			if (currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 64;
			}
		} else {
			if (currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 64;
			}
		}
		animation.update();

		// set direction
		if (right) facingRight = true;
		if (left) facingRight = false;
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
	public void keyPressed(int k) {
		
	}
	public void keyReleased(int k) {
		
	}
	
}
