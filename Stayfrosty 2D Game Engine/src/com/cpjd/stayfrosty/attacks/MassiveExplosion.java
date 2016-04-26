package com.cpjd.stayfrosty.attacks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.tools.Animation;

/*
 * For the cube attack
 */
public class MassiveExplosion {
	
	private int x;
	private int y;
	private int xmap;
	private int ymap;
	
	private int width;
	private int height;
	
	private Animation animation;
	private static BufferedImage[] sprites;
	
	private boolean remove;
	
	public MassiveExplosion(int x, int y) {
		this.x = x;
		this.y = y;
		width = 32;
		height = 32;
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(20);
	}
	
	// Preload so it's faster
	public static void init() {
		try {
			sprites = new BufferedImage[91];
			
			for(int i = 1; i < 91; i++) {
				if(i < 10) sprites[i] = ImageIO.read(com.cpjd.stayfrosty.attacks.MassiveExplosion.class.getResourceAsStream("/MassiveExplosion/explosion_1000"+i+".png"));
				if(i >= 10) sprites[i] = ImageIO.read(com.cpjd.stayfrosty.attacks.MassiveExplosion.class.getResourceAsStream("/MassiveExplosion/explosion_100"+i+".png"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		animation.update();
		if(animation.hasPlayedOnce()) {
			remove = true;
		}
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x +  xmap - width / 2, y + ymap - height / 2, null);

	}
	
	
}
