package com.cpjd.stayfrosty.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Center;

public class Upgrades {
	/* Gaps count as a purchases, but aren't used
	 * Used numbers in bought[x][] 0-5, 7 - 8, 12
	 */
	
	// The maximum amount of upgrades
	private static final int maxTier = 5;
	
	// The cost for any given upgrade
	private static final int cost = 3;
	
	// Display names
	String[] names = {
		"Pistol - ", "Uzi - ","Shotgun - ","Ak47- ","M4A1 - ","Sniper - ","","Endurance - ","Health - ","Powerup time - ",
		"Armor - ",""
	};
	
	// All of the x position of the bubbles
	private int[][] bx = new int[names.length][maxTier];
	
	// All of the y position of the bubbles
	private int[][] by = new int[names.length][maxTier];
	
	// Stores if the items have been purchased
	public static boolean[][] bought = new boolean[13][maxTier];
	
	// Look and feel
	private int fontSize = 15;
	private Font font = new Font("Arial",Font.PLAIN,fontSize);
	private int bubbleSize = 12;
	
	// Technical things
	private int currentSelectionY;
	private int arrowY;
	
	// Images
	BufferedImage arrow;
	
	Load load;
	
	public Upgrades(Load load) {
		this.load = load;
		
		// Set the x & y positions for all the bubbles
		for(int i = 0; i < names.length; i++) {
			for(int j = 0; j < maxTier; j++) {
				bx[i][j] = Center.align(21) + (i * fontSize);
				if(!names[i].equals("")) by[i][j] = Center.aligny(19.9 + (i * 5));
				if(names[i].equals("")) {
					by[i][j] = Center.aligny(19.9 + ((i + 1) * 5));
				}
			}
		}
		
		// Defaults
		currentSelectionY = 0;
		
		
		for(int i = 0; i < load.getPistolUpgrades(); i++) {
			bought[0][i] = true;
		}
		
		for(int i = 0; i < load.getUziUpgrades(); i++) {
			bought[1][i] = true;
		}
		
		for(int i = 0; i < load.getShotgunUpgrades(); i++) {
			bought[2][i] = true;
		}
		
		for(int i = 0; i < load.getAkUpgrades(); i++) {
			bought[3][i] = true;
		}
		
		for(int i = 0; i < load.getM4Upgrades(); i++) {
			bought[4][i] = true;
		}
		
		for(int i = 0; i < load.getSniperUpgrades(); i++) {
			bought[5][i] = true;
		}
		
		// non weapons
		for(int i = 0; i < load.getEnduranceUpgrades(); i++) {
			bought[7][i] = true;
		}
		
		for(int i = 0; i < load.getHealthUpgrades(); i++) {
			bought[8][i] = true;
		}
		
		for(int i = 0; i < load.getPowerupUpgrades(); i++) {
			bought[9][i] = true;
		}
		
		for(int i = 0; i < load.getArmorUpgrades(); i++) {
			bought[10][i] = true;
		}
		
		for(int i = 0; i < load.getMixtapeUpgrades(); i++) {
			bought[12][i] = true;
		}
		
		// Load the selection arrow image
		try {
			arrow = ImageIO.read(getClass().getResourceAsStream("/Interface/select.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void update() {
		// Manage the arrow location (y only) 
		arrowY = by[currentSelectionY][0];
		if(currentSelectionY >= 7) arrowY = by[currentSelectionY + 1][0];
	}
	
	// Attempts to purchase an upgrade
	private void buy() {
		if(Player.currentMemes < cost) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(Player.currentMemes >= cost) { // If the player has enough money
			if(!bought[currentSelectionY][maxTier - 1] && currentSelectionY < 6) { // If the player hasn't maxed out the tier
				// Figure out which tier we need to purchase
				int amount = 0; // The amount of tiers already bought
				for(int j = 0; j < maxTier; j++) {
					if(bought[currentSelectionY][j] == true && currentSelectionY < 6) {
						amount++;
					}
					if(bought[currentSelectionY + 1][j] == true && currentSelectionY >= 6 && currentSelectionY < 10) {
						amount++;
					}
					if(bought[currentSelectionY + 2][j] == true && currentSelectionY >= 10) {
						amount++;
					}
				}

				// Complete the transaction
				if(currentSelectionY < 6) bought[currentSelectionY][amount] = true;
				if(currentSelectionY >= 6 && currentSelectionY < 10) bought[currentSelectionY + 1][amount] = true;
				if(currentSelectionY >= 10) bought[currentSelectionY + 2][amount] = true;
				Player.currentMemes -= cost;
				AudioPlayer.playSound(SKeys.Buy);
			}
			if(!bought[currentSelectionY + 1][maxTier - 1] && currentSelectionY >= 6 && currentSelectionY < 10) { // If the player hasn't maxed out the tier
				// Figure out which tier we need to purchase
				int amount = 0; // The amount of tiers already bought
				for(int j = 0; j < maxTier; j++) {
					if(bought[currentSelectionY][j] == true && currentSelectionY < 6) {
						amount++;
					}
					if(bought[currentSelectionY + 1][j] == true && currentSelectionY >= 6 && currentSelectionY < 10) {
						amount++;
					}
					if(bought[currentSelectionY + 2][j] == true && currentSelectionY >= 10) {
						amount++;
					}
				}
				
				// Complete the transaction
				if(currentSelectionY < 6) bought[currentSelectionY][amount] = true;
				if(currentSelectionY >= 6 && currentSelectionY < 10) bought[currentSelectionY + 1][amount] = true;
				if(currentSelectionY >= 10) bought[currentSelectionY + 2][amount] = true;
				

				if(currentSelectionY == 7) Player.health = Player.maxHealth;
				

				if(currentSelectionY == 9) Player.armor = Player.maxArmor;
				
				Player.currentMemes -= cost;
				AudioPlayer.playSound(SKeys.Buy);
			}
			if(!bought[currentSelectionY + 2][maxTier - 1] && currentSelectionY >= 10) { // If the player hasn't maxed out the tier
				// Figure out which tier we need to purchase
				int amount = 0; // The amount of tiers already bought
				for(int j = 0; j < maxTier; j++) {
					if(bought[currentSelectionY][j] == true && currentSelectionY < 6) {
						amount++;
					}
					if(bought[currentSelectionY + 1][j] == true && currentSelectionY >= 6 && currentSelectionY < 10) {
						amount++;
					}
					if(bought[currentSelectionY + 2][j] == true && currentSelectionY >= 10) {
						amount++;
					}
				}

				// Complete the transaction
				if(currentSelectionY < 6) bought[currentSelectionY][amount] = true;
				if(currentSelectionY >= 6 && currentSelectionY < 10) bought[currentSelectionY + 1][amount] = true;
				if(currentSelectionY >= 10) bought[currentSelectionY + 2][amount] = true;
				Player.currentMemes -= cost;
				AudioPlayer.playSound(SKeys.Buy);
			}
		}
	}
	
	public void draw(Graphics2D g) {
		// Defaults
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Tier", Center.align(30), Center.aligny(15));
		
		// Draw the names
		for(int i = 0, j = 22; i < names.length; i++) {
			g.drawString(names[i], Center.align(20) - Center.getSWidth(g, names[i]), Center.aligny(j));
			j+=5;
		}
		
		// Draw the bubbles
		for(int i = 0; i < names.length; i++) {
			for(int j = 0; j < maxTier; j++) {
				if(i == names.length - 1) break;
				
				// Set a different color if it's purchased
				if(bought[i][j]) g.setColor(Color.GREEN);
				if(!bought[i][j]) g.setColor(Color.BLACK);
				
				g.fillOval(bx[j][0], by[i][0], bubbleSize, bubbleSize);
			}
		}
		// Draw help for the mixtape player
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,13));
		if(bought[12][0]) g.drawString("Press J to open the Jukebox!",bx[5][0] + 40,by[11][0] + fontSize - 4);
		
		// Draw the arrow selector
		g.drawImage(arrow, Center.align(32), arrowY, null);
		
		// Draw the price indicator
		g.setColor(Color.WHITE);
		g.drawString("All upgrades cost $3", 5, GamePanel.HEIGHT - 9);
		
	}
	
	public void keyPressed(int k) {
		// Load the sound
		if(k == KeyEvent.VK_UP) {
			if(currentSelectionY > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelectionY--;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			if(currentSelectionY < names.length - 3) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelectionY++;
			}
		}
		if(k == KeyEvent.VK_ENTER) {
			buy();
		}
		
	}
	
	public void keyReleased(int k) {}
}
