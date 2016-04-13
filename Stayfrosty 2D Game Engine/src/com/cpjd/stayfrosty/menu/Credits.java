package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Center;

public class Credits extends GameState {
	
	// Display
	String[] items = {
			"Bryan Simulator",
			"by Cats Pajamas Developers",
			"",
			"Team",
			"Will \"techguy9984\"",
			"Daniel \"boblop\"",
			"",
			"Thanks to",
			"ForeignGuyMike",
			"Eric Skiff",
			"",
			"This program is not intended for commerical distribution.",
			"",
			"All logos shown or represented in this game",
			"are copyright and or trademark of their respective",
			"corporations.",
			"",
	};
	
	// Technical
	private int y;
	
	BufferedImage cpjd;
	
	public Credits(GameStateManager gsm, Load load, Save save) {
		super(gsm,load,save);

		y = GamePanel.HEIGHT;
		AudioPlayer.playMusic(SKeys.Credits);
		
		try {
			cpjd = ImageIO.read(getClass().getResourceAsStream("/CPJD/cpjdlogosmall.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		y--;
		
		if(y + (30 * items.length) < -15) {
			//AudioPlayer.stopAll();
			gsm.setState(GameStateManager.MENU);
		}
	}

	
	public void draw(Graphics2D g) {
		// Rendeirng
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Clear the screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// Draw items
		g.setColor(Color.BLACK);
		g.setFont(new Font("Comic Sans MS",Font.BOLD,15));
		
		g.drawImage(cpjd, Center.centeri(cpjd.getWidth()), y - cpjd.getHeight(), null);
		
		g.drawString("Press ESC to exit", 5, 15);
		
		for(int i = 0, j = 1; i < items.length; i++) {
			if(i == items.length - 1) g.setColor(Color.MAGENTA);
			g.drawString(items[i], Center.center(g, items[i]), y + (30 * j));
			j++;
		}
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) {
			//AudioPlayer.stopAll();
			gsm.setState(GameStateManager.MENU);
		}
		
	}

	
	public void keyReleased(int k) {}
	
}
