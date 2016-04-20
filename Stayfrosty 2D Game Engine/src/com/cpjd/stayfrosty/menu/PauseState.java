package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.InputStream;

import org.lwjgl.openal.AL;

import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Layout;

public class PauseState extends GameState {
	
	private Font font;
	
	public static boolean keyLock;
	
	private String[] options = {"Resume","Sound (ON)","Restart Level","Exit to menu","Exit"};
	
	String soundState;
	
	// Aesthetics
	private Font optionsFont;
	private int vertStart; // The y position of the first options item
	private int pad; // Space between options
	
	// Technical variables
	private int currentChoice = 0; // The option that is currently selected
	private boolean restarting;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		restarting = false;
		
		if(AudioPlayer.mute) soundState = "Sound  (OFF)";
		if(!AudioPlayer.mute) soundState = "Sound  (ON)";
		
		try {
			InputStream inStream = getClass().getResourceAsStream("/Fonts/8-BIT WONDER.TTF");
			Font rawFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
			optionsFont = rawFont.deriveFont(10.0f);
		} catch(Exception e) {}
		
		// fonts
		font = new Font("Century Gothic", Font.PLAIN, 15);

		vertStart = (int)Layout.aligny(35);
		pad = 10; // In percent
		
		keyLock = false;
		
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		if(restarting) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Restarting the level...", Layout.centerw(10), Layout.centerh(10));
			return;
		}	
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", Layout.centerString("Game Paused", g), Layout.aligny(20));

		// Draw the options
		for(int i = 0, j = 1; i < options.length; i++) {
			// box
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.MAGENTA);
			}
			g.fillRect(Layout.centerString(options[i], g) - 2, vertStart + Layout.aligny(j * pad) - 14, Layout.getStringWidth(g, options[i]) + 4, 20);
			
			g.setFont(optionsFont);

			g.setColor(Color.WHITE);
			g.drawString(options[i], Layout.centerString(options[i], g), vertStart + Layout.aligny(j * pad));
			j++;
		}
	}

	private void select() {
		if(currentChoice == 0) { // Resume
			gsm.setPaused(false);
			AudioPlayer.playSound(SKeys.Select);
		}
		if(currentChoice == 1) { // Togglesound
			if(AudioPlayer.mute) { 
				options[1] = "Sound (OFF)";
				AudioLoad.stopAll();
			} else options[1] = "Sound (ON)";
			AudioPlayer.mute = !AudioPlayer.mute;
		}
		if(currentChoice == 3) { // Exit to Menu
			AudioPlayer.playSound(SKeys.Select);
			//AudioPlayer.stopAll();
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);

		}
		if(currentChoice == 4) { // Exit game
			AudioPlayer.playSound(SKeys.Select);
			AL.destroy();
			System.exit(0);
		}
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) { select(); }
		if(k == KeyEvent.VK_DOWN) {
			if(currentChoice < options.length - 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentChoice++;
			}
		}
		if(k == KeyEvent.VK_UP) {
			if(currentChoice > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentChoice--;
			}
		}
		if(k == KeyEvent.VK_ESCAPE) gsm.setPaused(false);
		keyLock = true;
	}
	
	public void keyReleased(int k) {
		
	}

}

