package com.cpjd.stayfrosty.entity.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.MultiBullet;

/* Important variables to configure
 *  - health & damage
 * Description
 *  - The baby spoder will walk back and forth (starting towards the right) until it hits a collidable tile, then it will switch
 *    directions. It does melee damage. When it's mother spoder dies, it also dies. It moves very slowly.
 *    
 */
public class BabySpoder extends Enemy {

	private BufferedImage[] sprites;

	Random r;
	
	public BabySpoder(TileMap tm) {
		super(tm);
		
		r = new Random();

		projRange = 100;

		moveSpeed = 1.1;
		maxSpeed = 1.1;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		// Tile
		width = 32;
		height = 32;
		cwidth = 32;
		cheight = 28;

		health = maxHealth = 1;
		damage = 1;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[2];

			sprites[0] = spritesheet.getSubimage(256, 192, 32, 32);
			sprites[1] = spritesheet.getSubimage(288, 192, 32, 32);

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

		animation.update();
	}




}
