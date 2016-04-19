package com.cpjd.stayfrosty.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.util.Center;

public class Minimap {

	BufferedImage image;

	private int descalex;
	private int descaley;
	
	private boolean enabled;
	
	public Minimap(String path, int x, int y) {

		descalex = x;
		descaley = y;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void toggle() {
		enabled = !enabled;
	}
	
	public void draw(Graphics2D g) {
		if(enabled) {
			g.setColor(Color.BLACK);
			g.fillRect(Center.centeri(image.getWidth()), Center.centerh(image.getHeight()), image.getWidth(), image.getHeight());
			g.drawImage(image, Center.centeri(image.getWidth()), Center.centerh(image.getHeight()), image.getWidth() / descalex, image.getHeight() / descaley, null );
			
			// Bounding box
			g.setColor(Color.ORANGE);
			Stroke s = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.drawRect(Center.centeri(image.getWidth()) - 2, Center.centerh(image.getHeight()) - 2, image.getWidth(), image.getHeight());
			g.setStroke(s);
			
			// Level
			g.setColor(Color.WHITE);
			g.drawString(formatState(),Center.centeri(image.getWidth()), Center.centerh(image.getHeight() + 15));
			
		}
	}
	
	private String formatState() {
		String temp = "Error";
		return temp;
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_M) {
			toggle();
		}
	}
	
	

}
