package com.cpjd.stayfrosty.boss;

import java.awt.Color;
import java.awt.Graphics2D;
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
import com.cpjd.stayfrosty.util.Center;
import com.cpjd.stayfrosty.weapons.MultiBullet;

public class JohnCena extends Enemy {

	private BufferedImage[] sprites;

	private BufferedImage[] pAnim;
	
	Random r;
	
	Player p;
	
	boolean powerup;
	
	int powerupCount;
	
	public JohnCena(TileMap tm, Player p) {
		super(tm);
		
		this.p = p;
		
		powerupCount = 60 * 2;
		
		moveSpeed = 3.5;
		maxSpeed = 3.5;
		fallSpeed = 0.4;
		maxFallSpeed = 10.0;
		
		powerup = false;
		
		r = new Random();
		
		// Collision
		width = 128;
		height = 128;
		cwidth = 128;
		cheight = 118;

		health = maxHealth = 200;
		damage = 1;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/bosses.png"));

			sprites = new BufferedImage[4];
			pAnim = new BufferedImage[2];
			
			pAnim[0] = spritesheet.getSubimage(128 * 4, 256, 128, 128);
			pAnim[1] = spritesheet.getSubimage(128 * 5, 256, 128, 128);
			
			
			sprites[0] = spritesheet.getSubimage(0, 256, 128, 128);
			sprites[1] = spritesheet.getSubimage(128, 256, 128, 128);
			sprites[2] = spritesheet.getSubimage(128 * 2, 256, 128, 128);
			sprites[3] = spritesheet.getSubimage(128 * 3, 256, 128, 128);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);

		right = true;
		facingRight = true;

		mbullets = new ArrayList<MultiBullet>();
		
		AudioPlayer.loopMusic(SKeys.John_Cena);
		
	}

	public void setRange(int range) {
		this.projRange = range;
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
		
		health += 0.1;
		if(health > maxHealth) health = maxHealth;
		
		if(r.nextInt(200) < 1 && !powerup) powerup();
		
		// Update powerup time
		if(powerup) {
			powerupCount--;
			if(left) p.setPosition(getx() + 32, gety());
			if(right) p.setPosition(getx() - 32, gety());
		}
		if(powerupCount <= 0 && powerup) stopPowerup();
	}
	
	public void powerup() {
		powerup = true;
		powerupCount = 60 * 2;
		animation.setFrames(pAnim);
		p.setMovementLock(true);
	}
	
	public void stopPowerup() {
		powerup = false;
		animation.setFrames(sprites);
		p.setMovementLock(false);
		p.setPosition(Player.globalX, Player.globalY - 200);
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
		// Draw the health bar
		g.setColor(Color.BLACK);
		g.drawRect(Center.centeri(200) - 1, 9, 201, 21);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Center.centeri(200), 10, 200, 20);
		g.setColor(Color.RED);
		g.fillRect(Center.centeri(200), 10, (int)health, 20);
		
	}


}
