package com.cpjd.stayfrosty.entity.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.MultiBullet;

/* Important variables to configure
 *  - fireProb - Each tick, the probability of a mini-spoder spawning
 *  - health & damage
 * Description
 *  - The Spoder will walk back and forth (starting towards the right) until it hits a collidable tile, then it will switch
 *    directions. Each spoder does melee damage. Every tick, they have a small change to spawn a mini-spoder.
 *    
 */
public class Bird extends Enemy {

	private BufferedImage[] sprites;

	private int xrange; // Player.x +- this range

	private int startx;
	private int fireProb;
	private int fireDelay;
	
	public Bird(TileMap tm, int startx) {
		super(tm);
		
		this.startx = startx;
		
		fireProb = 25;
		fireDelay = 20;
		
		projRange = 1000;
		moveSpeed = 3.5;
		maxSpeed = 2.5;

		xrange = 200;
		
		notFire = false;
		
		// Tile
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 32;

		health = maxHealth = 2;
		damage = 1;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[4];

			sprites[0] = spritesheet.getSubimage(0, 384, 64, 64);
			sprites[1] = spritesheet.getSubimage(64, 384, 64, 64);
			sprites[2] = spritesheet.getSubimage(128, 384, 64, 64);
			sprites[3] = spritesheet.getSubimage(192, 384, 64, 64);

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

	private void getNextPosition() {
		// Follows the player, always tries to stay a fixed height above the player, moves make and forth while doing so
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

	}

	public void update() {
		// update pos
		getNextPosition();
		checkTileMapCollision();
		
		setPosition(xtemp, ytemp);

		// movement
		if (right && getx() > (startx + xrange)) {
			right = false;
			left = true;
			facingRight = false;
		}
		if (left && getx() < (startx - xrange)) {
			left = false;
			right = true;
			facingRight = true;
		}
		
		// If they hit a wall
		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
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

		for (int i = 0; i < mbullets.size(); i++) {
			mbullets.get(i).update();
			if (mbullets.get(i).shouldRemove()) {
				mbullets.remove(i);
				i--;
			}
		}

		if(r.nextInt(100) < fireProb) fire();
		
		
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
			MultiBullet bullet = new MultiBullet(tileMap, getx(), gety(), (int) px, (int) py, true);
			bullet.setPosition(ex, ey);
			mbullets.add(bullet);
			
			AudioPlayer.playSound(SKeys.Enemy_Laser);
		}

	}


}
