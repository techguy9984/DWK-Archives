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
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.util.Center;
import com.cpjd.stayfrosty.weapons.MultiBullet;

public class Bieber extends Enemy {

	private BufferedImage[] sprites;

	Random r;
	
	Player p;
	
	boolean powerup;
	
	public Bieber(TileMap tm, Player p) {
		super(tm);
		
		this.p = p;
		
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
		cheight = 105;

		health = maxHealth = 100;
		damage = 1;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/bosses.png"));

			sprites = new BufferedImage[4];

			sprites[0] = spritesheet.getSubimage(0, 0, 128, 128);
			sprites[1] = spritesheet.getSubimage(128, 0, 128, 128);
			sprites[2] = spritesheet.getSubimage(128 * 2, 0, 128, 128);
			sprites[3] = spritesheet.getSubimage(128 * 3, 0, 128, 128);

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);

		right = true;
		facingRight = true;

		mbullets = new ArrayList<MultiBullet>();
		
		AudioPlayer.loopMusic(SKeys.Baby);
		
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

		fire();
		
		animation.update();
		
		health += 0.1;
		if(health > maxHealth) health = maxHealth;
				
		if(r.nextInt(500) < 1) {
			powerup();
		}
		if(powerup) {
			counter2++;
			if(counter2 > 60 * 2) {
				stopPowerup();
			}
		}
		
	}

	int counter = 0; // Slight delay

	int counter2 = 0;
	
	public void powerup() {
		p.slow();
		powerup = true;
	}
	
	public void stopPowerup() {
		p.resetSlow();
		powerup = false;
		counter2 = 0;
	}
	
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
		if(counter > 28) {
			counter = 0;
			MultiBullet bullet = new MultiBullet(tileMap, getx(), gety(), (int) px, (int) py, false);
			bullet.setImage("/Weapons/baby.png");
			bullet.setPosition(ex, ey);
			mbullets.add(bullet);
			
			//AudioPlayer.playSound(SKeys.Enemy_Laser);
		}			
	}
	public void draw(Graphics2D g) {
		super.draw(g);
		
		// Draw the health bar
		g.setColor(Color.BLACK);
		g.drawRect(Center.centeri(200) - 1, 9, 201, 21);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Center.centeri(200), 10, 200, 20);
		g.setColor(Color.RED);
		g.fillRect(Center.centeri(200), 10, (int)health * 2, 20);
		
		// Powerup
		if(powerup) {
			g.setColor(Color.CYAN);
			g.fillRect(r.nextInt(GamePanel.WIDTH),r.nextInt(GamePanel.HEIGHT),50,50);
		}
	}


}
