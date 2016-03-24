package com.cpjd.stayfrosty.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Center;

public class Inventory extends GameState {
	
	int[] purchases;
	
	public static int equip = -1;
	
	private int currentSelection = 0;
	
	private int numItems;
	
	BufferedImage selected;
	
	int ystart;
	
	public static int tab;
	
	InvConsume invc;
	
	Stats stats;
	
	public Inventory(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		tab = 1;
		
		invc = new InvConsume(gsm);
		
		stats = new Stats();
		
		resetTransition();
		
		try {
			selected = ImageIO.read(getClass().getResourceAsStream("/Interface/select.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		purchases = Shop.purchases;
		
		numItems = 0;
		
		for(int i = 0; i < purchases.length; i++) {
			
			if(purchases[i] == 1) numItems++;
		}
		
	}
	
	public static void addItem(int itemID) {
		
	}
	
	public void resetTransition() {
		ystart = GamePanel.HEIGHT;
	}
	
	public void update() {
		
		if(ystart > 20) ystart -= 15;
		if(ystart < 20) ystart = 20;
		
		invc.update();
		
		stats.update();
	}

	
	public void draw(Graphics2D g) {
		// Clear screen
		g.setColor(Color.lightGray);
		g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
		
		// Draw item borders and titles
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,20));
		g.drawString("Weapons: ", Center.getPointsCenter(0, Center.align(33), "Weapons: ", g), Center.aligny(10));		
		g.drawLine(Center.align(33), 0, Center.align(33), GamePanel.HEIGHT);
		g.drawLine(Center.align(66), 0, Center.align(66), GamePanel.HEIGHT);
		g.drawString("Items: ", Center.getPointsCenter(Center.align(33), Center.align(66), "Items: ", g), Center.aligny(10));
		//g.drawString("Quests: ", Center.getPointsCenter(Center.align(66), GamePanel.WIDTH, "Quests: ", g), Center.aligny(10));
		g.setFont(new Font("Arial",Font.BOLD,10));
		g.drawString("Press Enter to equip items. ",5,12);
		g.drawString("Press [ for stats.", 5, GamePanel.HEIGHT - 8);	

		// Show items
		g.setFont(new Font("Arial",Font.BOLD,12));
		if(purchases[0] == 1) g.drawString("-Pistol", 5, Center.aligny(10) + ystart);
		if(purchases[1] == 1) g.drawString("-Uzi", 5, Center.aligny(10) + ystart * 2);
		if(purchases[2] == 1) g.drawString("-Shotgun", 5, Center.aligny(10) + ystart * 3);
		if(purchases[3] == 1) g.drawString("-Ak-47", 5, Center.aligny(10) + ystart * 4);
		if(purchases[4] == 1) g.drawString("-M4A1", 5, Center.aligny(10) + ystart * 5);
		if(purchases[5] == 1) g.drawString("-Sniper Rifle", 5, Center.aligny(10) + ystart * 6);
		
		// Manage equip
		if(numItems != 0) manageEquip(g);
		// Underline current selection
		if(numItems != 0) manageSelection(g);
		
		invc.draw(g);
		
		// Draw help
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,10));
		g.drawString("Q | E & WASD to navigate inventory",Center.center(g, "Q | E & WASD to navigate inventory"), 20);
		
		stats.draw(g);
	}

	private void manageEquip(Graphics2D g) {
		// Draw equip
		FontMetrics metrics = g.getFontMetrics();
		if (equip == -1) g.drawImage(selected, -20, -20, null);
		if (equip == 0) g.drawImage(selected, 5 + metrics.stringWidth("-Pistol") + 5, Center.aligny(10) + ystart - 10, null);
		if (equip == 1) g.drawImage(selected, 5 + metrics.stringWidth("-Uzi") + 5, Center.aligny(10) + ystart * 2 - 10, null);
		if (equip == 2) g.drawImage(selected, 5 + metrics.stringWidth("-Shotgun") + 5, Center.aligny(10) + ystart * 3 - 10, null);
		if (equip == 3) g.drawImage(selected, 5 + metrics.stringWidth("-Ak-47") + 5, Center.aligny(10) + ystart * 4 - 10, null);
		if (equip == 4) g.drawImage(selected, 5 + metrics.stringWidth("-M4A1") + 5, Center.aligny(10) + ystart * 5- 10, null);
		if (equip == 5) g.drawImage(selected, 5 + metrics.stringWidth("-Sniper Rifle") + 5, Center.aligny(10) + ystart * 6 - 10, null);
		

	}

	private void manageSelection(Graphics2D g) {
		g.setColor(Color.GREEN);
		
		if(tab == 1) g.fillRect(5, Center.aligny(10) + ystart * (currentSelection + 1) + 3, 50, 5);

	}
	
	public void keyPressed(int k) {
		invc.keyPressed(k);
		if(k == KeyEvent.VK_UP) {
			if(currentSelection > 0) {
				
				AudioPlayer.playSound(SKeys.Change);
				currentSelection --;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			if(currentSelection < numItems - 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection ++;
			}
		}
		if(k == KeyEvent.VK_ENTER && numItems > 0) {
			
			if(tab != 1) return;
			
			if(currentSelection == equip) {
				equip = -1;
			} else {
				equip = currentSelection;
			}
		}
		if(k == KeyEvent.VK_E) {
			if(tab == 1) {
				tab = 2;
			}
		}
		if(k == KeyEvent.VK_Q) {
			if(tab == 2) {
				tab = 1;
			}
		}
		if(k == 91) {
			if(!Stats.enabled) stats.reset();
			Stats.enabled = !Stats.enabled;
		}
	}
	

	
	public void keyReleased(int k) {
		invc.keyReleased(k);
		
	}
	
}
