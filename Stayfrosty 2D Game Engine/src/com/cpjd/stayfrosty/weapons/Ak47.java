package com.cpjd.stayfrosty.weapons;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Ak47 extends Weapon {

	public Ak47(TileMap tm, Load load) {
		super(tm, load);
		
		damage = 3;
		accuracy = 0.4;
		
		delay = 100;
		
		// Statistics
		maxAmmoInClip = 30;
		maxAmmo = 90;
		
		// Current
		ammo[3] = load.getAkAmmo();
		clip[3] = load.getAkClip();
		
		burst = 3;

		loadSound(SKeys.Ak_Fire,SKeys.Ak_Reload);
		
		rightOffset = 9;
		leftOffset = 55;
		yOffset = 25;

		
		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi[0] = weapons.getSubimage(0, 32, 64, 32);
			bi[1] = weapons.getSubimage(64, 32, 64, 32);

			animation.setFrames(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		super.update();
		
		if(Upgrades.bought[3][0]) damage = 4;
		if(Upgrades.bought[3][1]) accuracy = 0.3;
		if(Upgrades.bought[3][2]) maxAmmo = 120;
		if(Upgrades.bought[3][3]) accuracy = 0.2;
		if(Upgrades.bought[3][4]) damage = 5;
		
		
	}

}
