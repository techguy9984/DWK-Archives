package com.cpjd.stayfrosty.weapons;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Uzi extends Weapon {

	public void setFiring(boolean b) {
		firing = b;
	}

	public Uzi(TileMap tm, Load load) {
		super(tm, load);
		damage = 1;
		accuracy = 0.6;

		maxAmmoInClip = 32;
		maxAmmo = 152;

		ammo[1] = load.getUziAmmo();
		clip[1] = load.getUziClip();

		burst = 4;
		
		loadSound(SKeys.Uzi_Fire,SKeys.Uzi_Reload);


		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi = new BufferedImage[2];

			bi[0] = weapons.getSubimage(0, 64, 64, 32);
			bi[1] = weapons.getSubimage(64, 64, 32, 32);

			animation.setFrames(bi);

		} catch (IOException e) {
			e.printStackTrace();
		}

		rightOffset = 16;
		leftOffset = 49;
		yOffset = 22;

	}
	
	public void update() {
		super.update();
		
		// Handle upgrades
		if(Upgrades.bought[1][0]) damage = 2;
		if(Upgrades.bought[1][1]) accuracy = 0.4;
		if(Upgrades.bought[1][2]) maxAmmo = 300;
		if(Upgrades.bought[1][3]) accuracy = 0.3;
		if(Upgrades.bought[1][4]) damage = 3;
		
	}

}
