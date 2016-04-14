package com.cpjd.stayfrosty.loading;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Layout;

public class Loading implements Runnable {
	
	// Thread
	Thread thread;
	
	// Image
	BufferedImage loadImg;
	Graphics2D g;
	
	// Percent
	private int percent;
	
	public Loading(Graphics2D g) {
		if(thread == null) {
			this.g = g;
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		try {		
			loadImg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/wood.png"));
		} catch(Exception e) {
			return;
		}
	}
	
	public void stop() {
		try {
			thread.join();
			percent = 100;
		} catch(Exception e) {}
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	public void draw(Graphics2D g) {
		if(loadImg != null) {
			g.drawImage(loadImg, 0, 0, null);
			
			// Draw loading bar
			g.setColor(Color.WHITE);
			g.fillRect((int)Layout.centerw(300, GamePanel.WIDTH) - 1, (int)Layout.centerh(50, GamePanel.HEIGHT) - 1, 301, 51);
			g.setColor(Color.BLACK);
			g.fillRect((int)Layout.centerw(300, GamePanel.WIDTH), (int)Layout.centerh(50, GamePanel.HEIGHT), 301, (percent / 100) * 300);
		}
	}
	
	
	
	
}
