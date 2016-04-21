package com.cpjd.stayfrosty.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.players.Daniel;

@SuppressWarnings("unused")
public class HUD {
	
	private Daniel player; // All the player stuff is important
	private BufferedImage image;

	public HUD(Daniel p) {
		player = p;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.png"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 10, null);
	}

	public void update() {

	}
}
