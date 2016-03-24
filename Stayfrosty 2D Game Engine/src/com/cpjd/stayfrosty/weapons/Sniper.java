package com.cpjd.stayfrosty.weapons;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Sniper extends Weapon {

	public Sniper(TileMap tm, Load load) {
		super(tm, load);
		
		damage = 15;
		accuracy = 0;
		
		delay = 100;
		
		// Statistics
		maxAmmoInClip = 3;
		maxAmmo = 15;
		
		// Current
		ammo[5] = load.getSniperAmmo();
		clip[5] = load.getSniperClip();
		
		loadSound(SKeys.Sniper_Fire,SKeys.Sniper_Reload);
		
		burst = 1;

		rightOffset = 9;
		leftOffset = 55;
		yOffset = 25;

		
		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi[0] = weapons.getSubimage(0, 128, 64, 32);
			bi[1] = weapons.getSubimage(64, 128, 64, 32);

			animation.setFrames(bi);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		super.update();
		
		
		if(Upgrades.bought[5][0]) damage = 16;
		if(Upgrades.bought[5][1]) damage = 17;
		if(Upgrades.bought[5][2]) maxAmmoInClip = 4;
		if(Upgrades.bought[5][3]) maxAmmo = 20;
		if(Upgrades.bought[5][4]) damage = 20;
		
	}

}
