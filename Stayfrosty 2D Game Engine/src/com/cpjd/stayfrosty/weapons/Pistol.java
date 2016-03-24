package com.cpjd.stayfrosty.weapons;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Pistol extends Weapon {

	public Pistol(TileMap tm, Load load) {
		super(tm, load);

		damage = 1;
		accuracy = 0.8;

		
		delay = 100;

		// Statistics
		maxAmmoInClip = 6;
		maxAmmo = 30;

		// Current
		ammo[0] = load.getPistolAmmo();
		clip[0] = load.getPistolClip();

		loadSound(SKeys.Pistol_Fire, SKeys.Pistol_Reload);

		burst = 1;
		
		rightOffset = 47;
		leftOffset = 16;
		yOffset = 18;
		

		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi[0] = weapons.getSubimage(0, 0, 32, 32);
			bi[1] = weapons.getSubimage(32, 0, 32, 32);

			animation.setFrames(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void update() {
		super.update();
		
		// Handle upgrades
		if(Upgrades.bought[0][0]) damage = 2;
		if(Upgrades.bought[0][1]) accuracy = 0.6;
		if(Upgrades.bought[0][2]) maxAmmo = 40;
		if(Upgrades.bought[0][3]) damage = 3;
		if(Upgrades.bought[0][4]) maxAmmo = 60;
		
	}

}
