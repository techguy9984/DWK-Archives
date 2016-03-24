package com.cpjd.stayfrosty.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.consume.Health;
import com.cpjd.stayfrosty.consume.Shield;
import com.cpjd.stayfrosty.consume.Skip;
import com.cpjd.stayfrosty.consume.Powerup;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.util.Center;

public class Consumables {
	
	private int currentSelection;
	
	// Purchases
	public static Stack<Health> healths;
	public static Stack<Shield> shields;
	public static Stack<Skip> skips;
	public static Stack<Powerup> powerups;
	
	// Images
	private BufferedImage health;
	private BufferedImage powerup;
	private BufferedImage shield;
	private BufferedImage skip;
	private BufferedImage select;
	
	// Technical
	private int sy;
	GameStateManager gsm;
	
	Load load;
	
	public Consumables(GameStateManager gsm, Load load) {
		this.load = load;
		
		sy = Center.align(20);
		currentSelection = 1;
		
		this.gsm = gsm;
		
		healths = new Stack<Health>();
		shields = new Stack<Shield>();
		skips = new Stack<Skip>();
		powerups = new Stack<Powerup>();
		
		for(int i = 0; i < load.getHealthPacks(); i++) {
			healths.add(new Health());
		}
		
		for(int i = 0; i < load.getShieldPacks(); i++) {
			shields.add(new Shield());
		}
		
		for(int i = 0; i < load.getSkipPacks(); i++) {
			skips.add(new Skip());
		}
		
		for(int i = 0; i < load.getPowerupPacks(); i++) {
			powerups.add(new Powerup());
		}
		
		try {
			health = ImageIO.read(getClass().getResourceAsStream("/Shop/health.png"));
			shield = ImageIO.read(getClass().getResourceAsStream("/Shop/shield.png"));
			skip = ImageIO.read(getClass().getResourceAsStream("/Shop/skip.png"));
			powerup = ImageIO.read(getClass().getResourceAsStream("/Shop/powerup.png"));
			select = ImageIO.read(getClass().getResourceAsStream("/Shop/select2.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		manageAvailable();
		manageSelect();
	}
	
	// Moves the select image to the current selection
	private void manageSelect() {
		if(currentSelection == 1) sy = Center.aligny(20);
		if(currentSelection == 2) sy = Center.aligny(35);
		if(currentSelection == 3) sy = Center.aligny(50);
		if(currentSelection == 4) sy = Center.aligny(65);
	}
	
	// Checks if the item is available for purchased
	private void manageAvailable() {
		if(currentSelection == 1) {
			if(Player.currentMemes >= 3) switchSelect("select2");
			else switchSelect("unselect2");
		}
		if(currentSelection == 2) {
			if(Player.currentMemes >= 10) switchSelect("select2");
			else switchSelect("unselect2");
		}
		if(currentSelection == 3) {
			if(Player.currentMemes >= 8) switchSelect("select2");
			else switchSelect("unselect2");
		}
		if(currentSelection == 4) {
			if(Player.currentMemes >= 20) switchSelect("select2");
			else switchSelect("unselect2");
		}
	}
	
	// Changes the select image from yellow to red or vice versa
	private void switchSelect(String name) {
		try {
			select = ImageIO.read(getClass().getResourceAsStream("/Shop/"+name+".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Attempts to purchase the item
	private void buy() {
		if(currentSelection == 1 && Player.currentMemes >= 3) {
			healths.add(new Health());
			Player.currentMemes -= 3;
			AudioPlayer.playSound(SKeys.Buy);
		} else if(currentSelection == 1 && Player.currentMemes < 3) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 2 && Player.currentMemes >= 10) {
			powerups.add(new Powerup());
			Player.currentMemes -= 10;
			AudioPlayer.playSound(SKeys.Buy);
		} else if(currentSelection == 2 && Player.currentMemes < 10) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 3 && Player.currentMemes >= 8) {
			shields.add(new Shield());
			Player.currentMemes -= 8;
			AudioPlayer.playSound(SKeys.Buy);
		} else if(currentSelection == 3 && Player.currentMemes < 8) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 4 && Player.currentMemes >= 20) {
			skips.add(new Skip());
			Player.currentMemes -= 20;
			AudioPlayer.playSound(SKeys.Buy);
		} else if(currentSelection == 4 && Player.currentMemes < 20) {
			AudioPlayer.playSound(SKeys.Error);
		}
	}
	public void draw(Graphics2D g) {
		// Draw items
		g.drawImage(health, Center.align(2), Center.aligny(20), null);
		g.drawImage(powerup, Center.align(2), Center.aligny(35), null);
		g.drawImage(shield, Center.align(2), Center.aligny(50), null);
		g.drawImage(skip, Center.align(2), Center.aligny(65), null);
		
		// Draw amounts
		g.drawString(String.valueOf(Consumables.healths.size()) +" x", Center.align(29), Center.aligny(29));
		g.drawString(String.valueOf(Consumables.powerups.size()) + " x", Center.align(29), Center.aligny(43));
		g.drawString(String.valueOf(Consumables.shields.size()) + " x", Center.align(29), Center.aligny(59));
		g.drawString(String.valueOf(Consumables.skips.size()) + " x", Center.align(29), Center.aligny(73.8));
		
		// Draw selection image
		g.drawImage(select, Center.align(2) - 1, sy - 1, null);
		
		// Draw ammo refill
		g.setFont(new Font("Arial",Font.PLAIN,15));
		g.setColor(Color.GREEN);
		g.drawString("For $5, press Y to refill the currently equipped weapon.", Center.align(2.2), Center.aligny(85));
	}
	
	public void keyPressed(int k) {
		if(currentSelection > 1 && k == KeyEvent.VK_UP) {
			AudioPlayer.playSound(SKeys.Change);
			currentSelection --;
		}
		if(currentSelection < 4 && k == KeyEvent.VK_DOWN) {
			AudioPlayer.playSound(SKeys.Change);
			currentSelection ++;
		}
		if(k == KeyEvent.VK_Y) {
			if(Player.currentMemes >= 5 && Inventory.equip != -1 && !Player.refill) {
				AudioPlayer.playSound(SKeys.Buy);
				Player.refill = true;
				Player.currentMemes -= 5;
			} else {
				AudioPlayer.playSound(SKeys.Error);
			}
		}
		if(k == KeyEvent.VK_ENTER) {
			buy();
		}
	}
	public void keyReleased(int k) {}	
}
