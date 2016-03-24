package com.cpjd.stayfrosty.weapons;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Shotgun extends Weapon {

	public Shotgun(TileMap tm, Load load) {
		super(tm, load);

		damage = 7;
		accuracy = 1;

		delay = 100;

		maxAmmoInClip = 3;
		maxAmmo = 20;

		ammo[2] = load.getShotgunAmmo();
		clip[2] = load.getShotgunClip();

		burst = 1;

		loadSound(SKeys.Shotgun_Fire, SKeys.Shotgun_Reload);

		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi[0] = weapons.getSubimage(0, 193, 64, 32);
			bi[1] = weapons.getSubimage(64, 193, 64, 32);

			animation.setFrames(bi);

		} catch (IOException e) {
			e.printStackTrace();
		}

		rightOffset = 15;
		leftOffset = 52;
		yOffset = 26;
	}
	
	public void update() {
		
		super.update();
		
		if(Upgrades.bought[2][0]) damage = 10;
		if(Upgrades.bought[2][1]) accuracy = 0.9;
		if(Upgrades.bought[2][2]) maxAmmoInClip = 4;
		if(Upgrades.bought[2][3]) accuracy = 0.8;
		if(Upgrades.bought[2][4]) damage = 12;
		
	}
}
