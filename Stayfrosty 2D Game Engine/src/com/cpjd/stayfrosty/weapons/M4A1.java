package com.cpjd.stayfrosty.weapons;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class M4A1 extends Weapon {

	public M4A1(TileMap tm, Load load) {
		super(tm, load);
		
		damage = 4;
		accuracy = 0.2;
		
		// Stats
		maxAmmoInClip = 36;
		maxAmmo = 100;
		
		// Current
		ammo[4] = load.getM4Ammo();
		clip[4] = load.getM4Clip();
		
		loadSound(SKeys.M4_Fire, SKeys.M4_Reload);
		
		burst = 4;
	
		rightOffset = 9;
		leftOffset = 55;
		yOffset = 25;
		
		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi[0] = weapons.getSubimage(0, 160, 64, 32);
			bi[1] = weapons.getSubimage(64, 160, 64, 32);

			animation.setFrames(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		super.update();
		
		if(Upgrades.bought[4][0]) damage = 5;
		if(Upgrades.bought[4][1]) accuracy = 0.3;
		if(Upgrades.bought[4][2]) maxAmmo = 132;
		if(Upgrades.bought[4][3]) accuracy = 0.1;
		if(Upgrades.bought[4][4]) damage = 6;
		
	}
	
}
