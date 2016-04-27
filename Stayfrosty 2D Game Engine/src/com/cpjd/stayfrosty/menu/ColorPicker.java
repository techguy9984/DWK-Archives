package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Layout;

public class ColorPicker {
	
	private boolean active;
	private int x;
	
	public ColorPicker() {
		reset();
	}
	
	public void update() {
		if(!active) return;
		
		if(x > Layout.centerw(300)) {
			x -= 50;
		}
	}
	
	public void requestChange() {
		active = !active;
		
		if(!active) reset();
	}
	
	
	public void reset() {
		x = GamePanel.WIDTH + 400;
	}
	
	public void draw(Graphics2D g) {
		if(!active) return;
		
		g.setColor(GameStateManager.hud);		
		g.fillRect(x, Layout.centerh(300), 300, 300);
		
		g.setColor(new Color(255 - GameStateManager.hud.getRed(), 255 - GameStateManager.hud.getBlue(), 255 - GameStateManager.hud.getGreen()));
		g.drawString("Select an RGB value: ",5 + x, Layout.centerh(300) + 30);
		
		
	}
	public boolean isActive() {
		return active;
	}
	
}
