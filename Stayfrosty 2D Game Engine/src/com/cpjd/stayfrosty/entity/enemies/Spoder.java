package com.cpjd.stayfrosty.entity.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.main.GamePanel;
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
public class Spoder extends Enemy {

	private BufferedImage[] sprites;

	private int fireProb;

	private int maxSpawns; // The total number of extra spoders
	private int currentSpawns;
	
	public Spoder(TileMap tm) {
		super(tm);
		
		if(!GamePanel.highQuality) maxSpawns = 10;
		if(GamePanel.highQuality) maxSpawns = 30;

		babySpoders = new ArrayList<Enemy>();
		
		projRange = 400;
		fireProb = 3;
		
		moveSpeed = 3.5;
		maxSpeed = 2.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		// Tile
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 64;

		health = 6;
		damage = 4;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[4];

			sprites[0] = spritesheet.getSubimage(0, 192, 64, 64);
			sprites[1] = spritesheet.getSubimage(64, 192, 64, 64);
			sprites[2] = spritesheet.getSubimage(128, 192, 64, 64);
			sprites[3] = spritesheet.getSubimage(192, 192, 64, 64);

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

		if (r.nextInt(200) < fireProb && currentSpawns <= maxSpawns)
			spawn();

		animation.update();
	}

	int counter = 0; // Slight delay

	// When called, fires a bullet at the player's position
	public void spawn() {
		currentSpawns++;
		// 1) Get the player's position
		BabySpoder s = new BabySpoder(tileMap);
		s.setPosition(getx(), gety());
		babySpoders.add(s);
		

	}



}
