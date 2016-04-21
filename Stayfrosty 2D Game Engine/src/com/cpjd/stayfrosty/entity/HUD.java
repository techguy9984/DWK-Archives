package com.cpjd.stayfrosty.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.players.Daniel;

public class HUD {
	
	private Daniel player; // All the player stuff is important
	private BufferedImage image;
	private Font font;

	public HUD(Daniel p) {
		player = p;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.png"));
			font = new Font("arial",Font.PLAIN,14);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 10, null);
		// draw health
		g.setFont(font);
		g.setColor(Color.RED);
		int health = player.getHealth();
		if(health > player.getMaxHealth()) health = player.getMaxHealth();
		g.fillRect(20, 14, health * 3, 10);
		// Represent the total health
		g.setColor(Color.BLACK);
		g.drawLine(20 + player.getMaxHealth() * 3, 14, 20 + player.getMaxHealth() * 3, 23);

		// Draw ammo
		g.setColor(Color.WHITE);

		// Set fonts
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial",Font.PLAIN,12));
		
	}

	public void update() {

	}
}
