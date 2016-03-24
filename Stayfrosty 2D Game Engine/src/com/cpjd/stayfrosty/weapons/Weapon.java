package com.cpjd.stayfrosty.weapons;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Animation;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Inventory;
import com.cpjd.stayfrosty.shop.Stats;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Weapon {
	// Updated version

	// Images
	BufferedImage weapons;
	BufferedImage[] bi;
	Animation animation;
	protected int delay;

	// Global
	public static int[] ammo = new int[7];
	public static int[] clip = new int[7];
	
	// Important gun stats
	protected int maxAmmoInClip;
	protected int maxAmmo;
	Random r;

	protected ArrayList<Bullet> bullets;

	// Gun location
	protected TileMap tm;
	protected int x;
	protected int y;
	protected double xmap;
	protected double ymap;
	protected boolean facingRight; // Changes the possible degrees location

	// Note - fire rate controlled by sound length

	// Deep stats
	protected int speed; // How fast the bullets move
	protected int reloadTime; // In milliseconds, the time it takes to reload
	//protected int totalAmmo; // The total ammo, in the clip, and out of it
	protected int damage; // The damage that one bullet will inflict
	protected boolean firing; // Sets to true if the gun is firing
	protected int burst; // How many shots fire at one key press, clip must be divisable by burst
	protected boolean infiniteAmmo; // If true, ammo isn't consumed
	
	// Bullet will travel downwards this amount
	// The higher the value, the less accuracy, 0 for no impairment
	protected double accuracy; // The amount of pixels the bullet will travel
	
	// Drawing
	protected int leftOffset;
	protected int rightOffset;
	protected int yOffset;
	
	// Audio
	private String fire;
	private String reload;
	
	Load load;
	
	// Constructor
	protected Weapon(TileMap tm, Load load) {
		this.tm = tm;
		this.load = load;
		r = new Random();
		
		animation = new Animation();
		animation.setDelay(-1);
		
		bi = new BufferedImage[2];
		bullets = new ArrayList<Bullet>();
		
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public int getDamage() {
		return damage;
	}
	
	protected void loadSound(String fire, String reload) {
		this.fire = fire;
		this.reload = reload;
	}
	
	// Update the gun's position based off the player's position
	public void setPos(int x, int y, double xmap, double ymap, boolean facingRight) {
		this.x = x;
		this.y = y;
		this.xmap = xmap;
		this.ymap = ymap;
		this.facingRight = facingRight;
	}

	// Function that manages reloading
	protected void reload() {
		AudioPlayer.playSound(reload);
			if (ammo[Inventory.equip] - maxAmmoInClip >= 0) {
				int temp = maxAmmoInClip - clip[Inventory.equip];
				ammo[Inventory.equip] -= temp;
				clip[Inventory.equip] += temp;
			} else {
				int temp = ammo[Inventory.equip];
				ammo[Inventory.equip] = 0;
				clip[Inventory.equip] += temp;
			}
	}
	
	// For playing sound since it has to be in key thread for some strange reason
	protected boolean canReload() {
		if(clip[Inventory.equip] < maxAmmoInClip && ammo[Inventory.equip] > 0) {
			if(!AudioPlayer.isSoundRunning(reload)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// Listens for firing or reloading key presses
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_X) {
			
			if (clip[Inventory.equip] > 0 || infiniteAmmo) {
				animation.setDelay(delay);
				AudioPlayer.playSound(fire);
				
				for(int i = 0; i < burst; i++) {
					Bullet bullet = new Bullet(tm, facingRight, damage, accuracy);
					if (facingRight)
						bullet.setPosition(x + 28 + (i * 35), y + 2); // Start it a same place
															// as player
					if (!facingRight)
						bullet.setPosition(x - 30 - (i * 35), y + 3);
					if(Inventory.equip == 6) bullet.setImagePath("laser.png");
					Stats.fired++;
					bullets.add(bullet);
				}
				
				if(!infiniteAmmo) clip[Inventory.equip] -= burst;
				animation.setPlayed(false);
			}
		}
		if (k == KeyEvent.VK_D) {
			if(canReload()) reload();
		}
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_X) {
			if (!AudioPlayer.isSoundRunning(reload) && !AudioPlayer.isSoundRunning(fire)) {
				animation.setFrame(0);
				animation.setDelay(-1);
			}
		}
	}
	
	public void update() {
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
			if(bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				Stats.missed++;
				i--;
			}
		}
		
		animation.update();
		
		if(clip[Inventory.equip] == 0) {
			//reload();
		}
		
		if(animation.hasPlayedOnce()) animation.setFrame(0);
	}

	public void draw(Graphics2D g) {
		// Draw the gun
		if(facingRight) {
			g.drawImage(animation.getImage(),(int) (x + xmap - 64 / 2) + rightOffset,(int) (y + ymap - 32) + yOffset,null);
		}
		else {
			g.drawImage(animation.getImage(),(int) (x + xmap - 64 / 2) + leftOffset,(int) (y + ymap - 32) + yOffset, -animation.getImage().getWidth(),animation.getImage().getHeight(),null);
		}
		// Draw bullets
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
				
	}
	
	public void ammoBox(int tile1, int tile2) {
		if(ammo[Inventory.equip] >= maxAmmo || infiniteAmmo) {
			tm.setMap(tile1, tile2, 91);
			return;
		}
		AudioPlayer.playSound(SKeys.Collect);
		int temp = r.nextInt(15);
		if (temp == 0)
			temp = 1;
		ammo[Inventory.equip] += temp;
		if (ammo[Inventory.equip] > maxAmmo)
			ammo[Inventory.equip] = maxAmmo;
	}
	
	private void check() {
			if(Inventory.equip == 0) {
				if(Upgrades.bought[0][2]) maxAmmo = 40;
				if(Upgrades.bought[0][4]) maxAmmo = 60;
			}
			if(Inventory.equip == 1) {
				if(Upgrades.bought[1][2]) maxAmmo = 300;
			}
			if(Inventory.equip == 2) {
				if(Upgrades.bought[2][2]) maxAmmoInClip = 4;
			}
			if(Inventory.equip == 3) {
				if(Upgrades.bought[3][2]) maxAmmo = 120;
			}
			if(Inventory.equip == 4) {
				if(Upgrades.bought[4][2]) maxAmmo = 130;
			}
			if(Inventory.equip == 5) {
				if(Upgrades.bought[5][2]) maxAmmoInClip = 4;
				if(Upgrades.bought[5][3]) maxAmmo = 20;
			}
	}
	
	public void refill() {
		check();

		ammo[Inventory.equip] = maxAmmo;
		clip[Inventory.equip] = maxAmmoInClip;
	}
	
	public void setFiring(boolean b) {
		firing = b;
	}

}
