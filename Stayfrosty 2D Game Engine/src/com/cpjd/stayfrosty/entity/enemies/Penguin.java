package com.cpjd.stayfrosty.entity.enemies;

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
import com.cpjd.stayfrosty.weapons.MultiBullet;

/* Important variables to configure
 *  - health & damage
 * Description
 *  - The penguin will wait in prey for the player to come. It hides in an small egg and will burst when the player is near.
 *    The penguin hovers and will pursue the player relentelessy. It's y matches the player's y, making it almost 
 *    impossible to dodge. It fires constantly, with no possible way to dodge. It is extremely weak until it hatches.
 *    
 */
public class Penguin extends Enemy {

	private BufferedImage[] sprites;

	private BufferedImage[] egg;
	
	Random r;
	private boolean eggFinished;
	
	Animation eggAnim;

	private double starty;
	
	public Penguin(TileMap tm, double starty) {
		super(tm);
		
		eggFinished = false;

		projRange = 400;
		
		moveSpeed = 3.5;
		maxSpeed = 3.5;
		fallSpeed = 0.4;
		maxFallSpeed = 10.0;

		r = new Random();
		
		// Tile
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 52;

		health = maxHealth = 1;
		damage = 0;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/enemies.png"));

			sprites = new BufferedImage[4];

			egg = new BufferedImage[4];
			
			sprites[0] = spritesheet.getSubimage(0, 7 * 64, 64, 64);
			sprites[1] = spritesheet.getSubimage(64, 7 * 64, 64, 64);
			sprites[2] = spritesheet.getSubimage(128, 7 * 64, 64, 64);
			sprites[3] = spritesheet.getSubimage(192, 7 * 64, 64, 64);
			
			egg[0] = spritesheet.getSubimage(0, 8 * 64, 64, 64);
			egg[1] = spritesheet.getSubimage(64, 8 * 64, 64, 64);
			egg[2] = spritesheet.getSubimage(128, 8 * 64, 64, 64);
			egg[3] = spritesheet.getSubimage(192, 8 * 64, 64, 64);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100); // Slow

		eggAnim = new Animation();
		eggAnim.setFrames(egg);
		eggAnim.setDelay(1200);
		
		right = true;
		facingRight = true;

		mbullets = new ArrayList<MultiBullet>();
		
		this.starty = starty;
	}

	private void getNextPosition() {
		
		if (left && eggFinished) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		if (right && eggFinished) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}

		if(eggFinished) y = Player.globalY;
		else y = starty;
	}

	public void update() {
		// update pos
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// change stats once it hatches
		if(eggFinished) {
			damage = 5;
		}
		
		// determine dx
		if(getx() < Player.globalX) {
			right = true;
			left = false;
		}
		if(getx() > Player.globalX) {
			left = true;
			right = false;
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

		
		animation.update();
		
		if(calculateDifference(Player.globalX,Player.globalY,getx(),gety()) < GamePanel.WIDTH / 2) {
			eggAnim.update();
			if(eggAnim.hasPlayedOnce() && !eggFinished) {
				eggFinished = true;
				health = 15;
				maxHealth = 20;

			}
		}
		if(calculateDifference(Player.globalX,Player.globalY,getx(),gety()) < GamePanel.WIDTH / 2) {
			if(eggFinished) fire();
			if(r.nextInt(1500) < 10 && eggFinished) multifire();
		}

	}

	int counter = 0; // Slight delay

	public void multifire() {
		// Generate a series of points around ourselves in a circle
		double degree = 0;
		double orbx, orby; // The point
		
		
		
		for(int i = 0; i < 90; i++) {
			degree += 4;
			orbx = getx() + 25 * Math.cos(degree);
			orby = gety() + 25 * Math.sin(degree);
			
			MultiBullet bullet = new MultiBullet(tileMap, getx(), gety(), (int)orbx, (int)orby, true);
			bullet.setPosition(getx(), gety());
			mbullets.add(bullet);
			
		}
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
		if (calculateDifference(Player.globalX, Player.globalY, getx(), gety()) < projRange && counter > 40) {
			counter = 0;
			MultiBullet bullet = new MultiBullet(tileMap, getx(), gety(), (int) px, (int) py, false);
			bullet.setPosition(ex, ey);
			mbullets.add(bullet);
			
			AudioPlayer.playSound(SKeys.Enemy_Laser);
		}

	}

	public void draw(Graphics2D g) {
		super.draw(g);
		
		if(!eggFinished) g.drawImage(eggAnim.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
	}

}
