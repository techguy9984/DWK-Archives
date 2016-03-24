package com.cpjd.stayfrosty.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.external.imageprocessing.AnimatedGIF;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.shop.Inventory;
import com.cpjd.stayfrosty.util.Center;
import com.cpjd.stayfrosty.weapons.Weapon;

public class HUD {
	
	private Player player; // All the player stuff is important
	private BufferedImage image;
	private Font font;

	// GIFs
	private AnimatedGIF wow;
	private AnimatedGIF toad;

	// Memory
	Runtime runtime;
	private double usedMemory; // The memory currently being used
	private double totalMemory; // The total amount of memory that can be used, JVM can change this
	private double allocatedMemory; // The total amount of memory that can be used
	private int rectLength;
	
	public HUD(Player p) {
		player = p;
		
		runtime = Runtime.getRuntime();

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.png"));
			font = new Font("arial",Font.PLAIN,14);
			
			wow = new AnimatedGIF(0,GamePanel.HEIGHT - (225 / 2));
			wow.read("/MLG/wow.gif");
			wow.setAdjust(true);
			wow.setDescale(4);
			
			toad = new AnimatedGIF(220,GamePanel.HEIGHT - 103);
			toad.setAdjust(true);
			toad.read("/MLG/toad.gif");
			
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
		
		// Draw armor
		int armor = (int)Player.armor;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(20, 14, armor * 3, 10);
		g.setColor(Color.BLACK);
		
		// Draw endurance
		double endurance = player.getEndurance();
		if(endurance > player.getMaxEndurance()) endurance = player.getMaxEndurance();
		g.setColor(Color.YELLOW);
		g.fillRect(20, 34, (int)endurance * 5, 10);
		
		// Draw powerups
		double powerup = player.getCurrentPowerups();
		g.setColor(Color.MAGENTA);
		g.fillRect(20, 54 , (int)(powerup * 5), 10);
		
		// Draw dank memes
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf((int)player.getMemes()),25, 104);
		
		// Draw ammo
		g.setColor(Color.WHITE);
		if(Inventory.equip == 0) g.drawString(String.valueOf(Weapon.clip[Inventory.equip])+" | "+String.valueOf(Weapon.ammo[Inventory.equip]), 25, 84);
		if(Inventory.equip == 1) g.drawString(String.valueOf(Weapon.clip[Inventory.equip])+" |"+String.valueOf(Weapon.ammo[Inventory.equip]), 25, 84);
		if(Inventory.equip == 2) g.drawString(String.valueOf(Weapon.clip[Inventory.equip])+" | "+String.valueOf(Weapon.ammo[Inventory.equip]), 30, 84);
		if(Inventory.equip == 3) g.drawString(String.valueOf(Weapon.clip[Inventory.equip])+" | "+String.valueOf(Weapon.ammo[Inventory.equip]), 25, 84);
		if(Inventory.equip == 4) g.drawString(String.valueOf(Weapon.clip[Inventory.equip])+" |"+String.valueOf(Weapon.ammo[Inventory.equip]), 25, 84);
		if(Inventory.equip == 5) g.drawString(String.valueOf(Weapon.clip[Inventory.equip])+" | "+String.valueOf(Weapon.ammo[Inventory.equip]), 25, 84);
		if(Inventory.equip == 6) g.drawString("infinite", 30, 84);
		
		// Draw doritoes
		g.setColor(Color.ORANGE);
		if(GameStateManager.globalState <= 14) {	
			if(GameStateManager.globalState != GameStateManager.L_TUTORIAL
				&& GameStateManager.globalState != GameStateManager.L1_BOSS) g.drawString(String.valueOf(Player.currentDoritoes +" / "+(GameStateManager.globalState - 4)), 25, 124);
		}
		if(GameStateManager.globalState >= 15 && GameStateManager.globalState != GameStateManager.L2_BOSS && GameStateManager.globalState < 26) {
			g.drawString(String.valueOf(Player.currentDoritoes +" / "+(GameStateManager.globalState - 15)), 25, 124);
		}
		if(GameStateManager.globalState >= 26 && GameStateManager.globalState != GameStateManager.L3_BOSS) {
			g.drawString(String.valueOf(Player.currentDoritoes +" / "+(GameStateManager.globalState - 26)), 25, 124);
		}
		
		
		// Draw gifs
		if(player.isSmoking()) {
			wow.draw(g);
			toad.draw(g);
		}	
		
		// Set fonts
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial",Font.PLAIN,12));
		
		if(GamePanel.debug) {
			g.fillRect(5, Center.aligny(89.9), rectLength, 11);
			g.setColor(Color.BLACK);
			g.drawString("Memory usage: "+usedMemory+ " MB / "+ totalMemory+" MB" + " / "+allocatedMemory+" MB", 5, Center.aligny(92));
			rectLength = Center.getSWidth(g, "Memory usage: "+usedMemory+ " MB / "+ totalMemory+" MB" + " / "+allocatedMemory+" MB");
		}
		
	}

	int memReport; // A timer using for memory reporting
	
	private void calcMemory() {
		// Calculate how much memory is being used
		usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
		totalMemory = (runtime.totalMemory()) / 1024 / 1024;
		allocatedMemory = (runtime.maxMemory()) / 1024 / 1024;
		
		memReport++;
		if(memReport % 60 == 0 && GamePanel.debug) {
			System.out.println("Memory usage: "+usedMemory+ " MB / "+ totalMemory+" MB" + " / "+allocatedMemory+" MB");
		}
	}
	
	int count = 1;
	public void update() {
		calcMemory();
		
		if(count % 3 == 0) {
			wow.nextFrame();
			toad.nextFrame();
		}
		count++;
	}
}