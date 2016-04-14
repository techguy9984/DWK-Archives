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
import com.cpjd.stayfrosty.consume.Health;
import com.cpjd.stayfrosty.consume.Shield;
import com.cpjd.stayfrosty.consume.Skip;
import com.cpjd.stayfrosty.consume.Powerup;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.shop.Consumables;
import com.cpjd.stayfrosty.shop.Inventory;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.util.Center;

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
	
	public PauseState(GameStateManager gsm, Load load, Save save) {
		
		super(gsm, load, save);
		
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

		vertStart = Center.aligny(35);
		pad = 10; // In percent
		
		keyLock = false;
		
	}
	
	public void update() {}
	
	public void setRestarting(boolean b) {
		if(b) Player.currentDoritoes = 0;
		restarting = b;
	}
	
	public void draw(Graphics2D g) {
		if(restarting) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Restarting the level...", Center.center(g, "Reloading Level..."), Center.centerv(g));
			return;
		}
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", Center.center(g, "Game Paused"), Center.aligny(20));

		// Draw the options
		for(int i = 0, j = 1; i < options.length; i++) {
			// box
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.MAGENTA);
			}
			g.fillRect(Center.center(g, options[i]) - 2, vertStart + Center.aligny(j * pad) - 14, Center.getSWidth(g, options[i]) + 4, 20);
			
			g.setFont(optionsFont);

			g.setColor(Color.WHITE);
			g.drawString(options[i], Center.center(g, options[i]), vertStart + Center.aligny(j * pad));
			j++;
		}
	}

	// Resets all save data that was earned during the level,
	// as well as some other things
	public void reset() {
		AudioPlayer.playSound(SKeys.Select);
		Inventory.equip = -1;
		//AudioPlayer.stopAll();
		restarting = true;
		Save.restart = true;
		Player.currentDoritoes = 0;
		gsm.setState(gsm.getState());
		gsm.setPaused(false);
		
		// Reset the consumables
		Consumables.healths.clear();
		Consumables.shields.clear();
		Consumables.skips.clear();
		Consumables.powerups.clear();
		
		for(int i = 0; i < load.getHealthPacks(); i++) {
			Consumables.healths.add(new Health());
		}
		
		for(int i = 0; i < load.getShieldPacks(); i++) {
			Consumables.shields.add(new Shield());
		}
		
		for(int i = 0; i < load.getSkipPacks(); i++) {
			Consumables.skips.add(new Skip());
		}
		
		for(int i = 0; i < load.getPowerupPacks(); i++) {
			Consumables.powerups.add(new Powerup());
		}
		
		// Reset the upgrades that aren't in the save file
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 5; j++) {
				Upgrades.bought[i][j] = false;
			}
		}
		
		for(int i = 0; i < load.getPistolUpgrades(); i++) {
			Upgrades.bought[0][i] = true;
		}
		
		for(int i = 0; i < load.getUziUpgrades(); i++) {
			Upgrades.bought[1][i] = true;
		}
		
		for(int i = 0; i < load.getShotgunUpgrades(); i++) {
			Upgrades.bought[2][i] = true;
		}
		
		for(int i = 0; i < load.getAkUpgrades(); i++) {
			Upgrades.bought[3][i] = true;
		}
		
		for(int i = 0; i < load.getM4Upgrades(); i++) {
			Upgrades.bought[4][i] = true;
		}
		
		for(int i = 0; i < load.getSniperUpgrades(); i++) {
			Upgrades.bought[5][i] = true;
		}
		
		// non weapons
		for(int i = 0; i < load.getEnduranceUpgrades(); i++) {
			Upgrades.bought[7][i] = true;
		}
		
		for(int i = 0; i < load.getHealthUpgrades(); i++) {
			Upgrades.bought[8][i] = true;
		}
		
		for(int i = 0; i < load.getPowerupUpgrades(); i++) {
			Upgrades.bought[9][i] = true;
		}
		
		for(int i = 0; i < load.getArmorUpgrades(); i++) {
			Upgrades.bought[10][i] = true;
		}
		
		for(int i = 0; i < load.getMixtapeUpgrades(); i++) {
			Upgrades.bought[12][i] = true;
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
		if(currentChoice == 2) { // Restart Level
			reset();
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

