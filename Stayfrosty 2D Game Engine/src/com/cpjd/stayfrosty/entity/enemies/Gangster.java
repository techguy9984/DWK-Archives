package com.cpjd.stayfrosty.entity.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.MultiBullet;

/* Important variables to configure
 * 	- activeRange - when the player enters this range, the enemy becomes active
 *  - moveSpeed - how fast gangster can move, player's speed is 3.3
 *  - enduranceChance / memeChance / healthChance - When a shot hits the player, the chance in % of the corresponding category
 *    being affected
 *  - enduranceLost / memeLost / healthLost - If the category is chosen, how much of each corresponding category is lost
 *  - health & damage
 * Description
 *  - Gangster will follow the player wherever they go, performing jumps at all the player's jump locations. If contact is
 *    made with ganster, powerup is lost. Otherwise, Gangster fires projectiles that have a change to take away
 *    memes, endurance, & health.
 *  - When gangster is killed, he drops the memes & doritoes he stole.
 *  - Stolen health is added to Snoop Doggs health 
 * IMPORTANT
 *  - In adition, the gangster setStartX(double x) method must be called. Pass in the same x value as in setPosition(x,y)
 */
public class Gangster extends Enemy {

	// Bounds
	private int boundRange;
	
	// Start position
	private double startx;
	
	private BufferedImage[] sprites;

	public Gangster(TileMap tm) {
		super(tm);
		
		projRange = 200;

		boundRange = 100;
		
		moveSpeed = 2.5;
		maxSpeed = 2.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		// Tile
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 64;

		health = 10;
		maxHealth = 15; // Difference is the amount of health snoop dogg can steal
		damage = 2;
		
		// Give a little extra memes to the player
		totalMemes = r.nextInt(3);
		
		memeProb = 50;
		doritoeProb = 40;
		healthProb = 60;
		

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[4];

			sprites[0] = spritesheet.getSubimage(0, 128, 64, 64);
			sprites[1] = spritesheet.getSubimage(64, 128, 64, 64);
			sprites[2] = spritesheet.getSubimage(128, 128, 64, 64);
			sprites[3] = spritesheet.getSubimage(192, 128, 64, 64);

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

	public void setStartX(double x) {
		startx = x;
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
			//hit(1);
		}
		
		// if it hits a wall, go other direction
		if(right && getx() > (startx + boundRange)) {
			right = false;
			left = true;
			facingRight = false;
		}
		if(left && getx() < (startx - boundRange)) {
			left = false;
			right = true;
			facingRight = true;
		}
		
		for (int i = 0; i < mbullets.size(); i++) {
			mbullets.get(i).update();
			if (mbullets.get(i).shouldRemove()) {
				mbullets.remove(i);
				i--;
			}
		}

		if (r.nextInt(100) < 5)
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
		if (calculateDifference(Player.globalX, Player.globalY, getx(), gety()) < projRange && counter > 20) {
			counter = 0;
			MultiBullet bullet = new MultiBullet(tileMap, getx(), gety(), (int) px, (int) py, false);
			bullet.setPosition(ex, ey);
			mbullets.add(bullet);
			
			AudioPlayer.playSound(SKeys.Enemy_Laser);
		}

	}


}
