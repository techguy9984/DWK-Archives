package com.cpjd.stayfrosty.entity.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.MultiBullet;

/* Important variables to configure
 * 	- projRange - the player has to be within this range for the enemy to fire
 *  - fireProb - Each tick, the probability of the enemy firing
 *  - fireDelay - Once the enemy has found the players position, wait this amount of time. If set to 0, it's impossible to dodge.
 *  - health & damage
 * Description
 *  - The Illuminati will walk back and forth (starting towards the right) until it hits a collidable tile, then it will switch
 *    directions. It fires based off an alrogithm that calculates the direct path to the player, it fires at a slight delay to allow 
 *    the player to dodge the attack. The Illuminati has no melee damage.
 *    
 */
public class Illuminati extends Enemy {

	private BufferedImage[] sprites;

	Random r;
	
	private int fireProb;
	private int fireDelay;
	
	public Illuminati(TileMap tm) {
		super(tm);
		
		projRange = 400;
		fireProb = 35;
		fireDelay = 20;
		
		moveSpeed = 2.5;
		maxSpeed = 2.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		r = new Random();
		
		// Tile
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 64;

		health = maxHealth = 3;
		damage = 1;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[4];

			sprites[0] = spritesheet.getSubimage(0, 0, 64, 64);
			sprites[1] = spritesheet.getSubimage(64, 0, 64, 64);
			sprites[2] = spritesheet.getSubimage(128, 0, 64, 64);
			sprites[3] = spritesheet.getSubimage(192, 0, 64, 64);

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100); // Slow

		right = true;
		facingRight = true;

		mbullets = new ArrayList<MultiBullet>();
		
		
	}

	public void setRange(int range) {
		this.projRange = range;
	}
	
	public void setProb(int prob) {
		this.fireProb = prob;
	}
	
	private void getNextPosition() {
		// Moves back and forth horizontally, changes direction upon collision
		
		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}

		if (falling) {
			dy += fallSpeed; // Might just run off a cliff
		}
	}

	public void update() {
		// update pos
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) - 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		if(hitingFatalBlock) {
			hit(1);
		}
		
		// if it hits a wall, go other direction
		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}

		for (int i = 0; i < mbullets.size(); i++) {
			mbullets.get(i).update();
			if (mbullets.get(i).shouldRemove()) {
				mbullets.remove(i);
				i--;
			}
		}

		if (r.nextInt(100) < fireProb)
			fire();

		animation.update();
	}

	int counter = 0; // Slight delay

	// When called, fires a bullet at the player's position
	public void fire() {

		// 1) Get the player's position
		
		// Calculate the enemy's position
		int ex = getx();
		int ey = gety();

		// Add the bullet
		// Make sure we are in range before we worry about firing
		double px = Player.globalX;
		double py = Player.globalY;

		counter++;
		// Test rise and run
		if (calculateDifference(Player.globalX, Player.globalY, getx(), gety()) < projRange && counter > fireDelay) {
			counter = 0;
			MultiBullet bullet = new MultiBullet(tileMap, getx(), gety(), (int) px, (int) py, false);
			bullet.setPosition(ex, ey);
			mbullets.add(bullet);
			
			AudioPlayer.playSound(SKeys.Enemy_Laser);
		}

	}


}
