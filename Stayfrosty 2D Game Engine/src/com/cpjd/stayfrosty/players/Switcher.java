package com.cpjd.stayfrosty.players;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.cpjd.tools.Layout;

public class Switcher {
	
	private boolean active;
	private boolean moving;
	
	public Switcher() {
		
	}
	
	public void update() {
		if(!active) return;
		
		
	}
	
	public void draw(Graphics2D g) {
		if(!active) return;
		
		g.setColor(Color.DARK_GRAY);
		g.fillOval(Layout.centerw(300), Layout.centerh(300), 300, 300);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Daniel", Layout.centerw(300) + 25, Layout.centerh(300) + 150);
	}

	public void requestChange() {
		active = !active;
	}
	
	public void keyPressed(int k) {
		
	}
	
}
