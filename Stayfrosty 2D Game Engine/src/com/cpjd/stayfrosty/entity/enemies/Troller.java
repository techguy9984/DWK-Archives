package com.cpjd.stayfrosty.entity.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.MultiBullet;

/* Important variables to configure
 * 	- activeRange - when the player enters this range, the enemy becomes active
 *  - escapeRange - if the player exits this range, the enemy becomes inactive
 *  - moveSpeed - how fast the troller can move, player's speed is 3.3
 *  - health & damage
 * Description
 *  - When the Troller becomes active, it will follow the player's movement. It will perform a jump at every location that the
 *    player did. It only can do melee damage
 */
public class Troller extends Enemy {
	
	private BufferedImage[] sprites;
	
	Random r;
	
	private boolean active; // If the player entered the range
	private int activeRange; // When the player this close to the enemy, it will become active
	private int escapeRange; // If the player runs far enough away, the enemy is set to be inactive
	
	public Troller(TileMap tm) {
		super(tm);
		
		r = new Random();
		
		projRange = 500;
		
		activeRange = 300;
		active = false;
		
		mbullets = new ArrayList<MultiBullet>();
		
		moveSpeed = 3.0;
		maxSpeed = 3.0;
		fallSpeed = 0.4;
		maxFallSpeed = 10.0;
		
		notFire = true;
		
		// Hit box 
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 58;
		
		// Jumping
		jumpStart = -13;
		stopJumpSpeed = 0.4;
		
		health = maxHealth = 8;
		damage = 1;
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[4];

			sprites[0] = spritesheet.getSubimage(0, 64, 64, 64);
			sprites[1] = spritesheet.getSubimage(64, 64, 64, 64);
			sprites[2] = spritesheet.getSubimage(128, 64, 64, 64);
			sprites[3] = spritesheet.getSubimage(192, 64, 64, 64);

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100); // Slow

		right = true;
		facingRight = true;

	}
	

	private void getNextPosition() {
		// Moves back and forth horizontally, changes direction upon collision

		// movement
		if (left && active) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		if (right && active) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}

		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			if (!verticalEnabled)
				falling = true;
		}

		// falling
		if (falling) {

			dy += fallSpeed;

			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping)
				if (!verticalEnabled)
					dy += stopJumpSpeed;

			if (dy > maxFallSpeed)
				if (!verticalEnabled)
					dy = maxFallSpeed;

		}
		
	}

	public void update() {
		// update pos
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if(Player.globalX < getx()) {
			right = false;
			left = true;
			facingRight = false;
		}
		if(Player.globalX > getx()) {
			left = false;
			right = true;
			facingRight = true;
		}
		
		if(hitingFatalBlock) {
			hit(0.1);
		}
		
		
		// Check for jumps
		for(int i = 0; i < Player.jumps.size(); i++) {
			if(getx() > (Player.jumps.get(i).x - 8) && getx() < (Player.jumps.get(i).x + 8)) {
				if(gety() > (Player.jumps.get(i).y - 8) && gety() < (Player.jumps.get(i).y + 8)) {
					setJumping(true);
					Player.jumps.remove(i);
				}
			}
		}
		
		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) - 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// Set the troller inactive if the player escapes to different location
		// vertically
		if (calculateDifference(Player.globalX, Player.globalY, getx(), gety()) < escapeRange) {
			active = false;
		}

		if(calculateDifference(Player.globalX, Player.globalY, getx(), gety()) < activeRange) {
			active = true;
		}
		
		// If we're really close, don't glitch the art
		if(calculateDifference(Player.globalX, Player.globalY, getx(), gety()) < 10) {
			right = true;
			facingRight = true;
		}
		
		// Also check vertically
		if(getx() > Player.globalX - 10 && getx() < Player.globalX + 10) {
			right = true;
			facingRight = true;
		}
		
		/* if it hits a wall, go other direction
		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}*/


		animation.update();
	}

	public int getDamage() {
		return damage;
	}

	public ArrayList<MultiBullet> getIllumBox() {
		return mbullets;
	}


}
