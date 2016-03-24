package com.cpjd.stayfrosty.weapons;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Laser extends Weapon {

	public Laser(TileMap tm, Load load) {
		super(tm, load);
		
		damage = 1;
		accuracy = 0;
		
		delay = 100;
		
		// Statistics
		infiniteAmmo = true;
		maxAmmoInClip = 0;
		maxAmmo = 0;
		
		// Current
		ammo[6] = maxAmmo;
		clip[6] = maxAmmoInClip;
		
		burst = 10;
		
		loadSound(SKeys.Laser_Fire,SKeys.M4_Reload);
		
		rightOffset = 17;
		leftOffset = 46;
		yOffset = 29;
		
		try {
			weapons = ImageIO.read(getClass().getResourceAsStream("/Weapons/weapons.png"));

			bi[0] = weapons.getSubimage(0, 225, 64, 32);
			bi[1] = weapons.getSubimage(64, 225, 64, 32);

			animation.setFrames(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

