package com.cpjd.stayfrosty.shop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.util.Center;

public class InvConsume {
	
	// Technical
	private int currentSelection;
	private int healthy;
	private int powerupy;
	private int shieldy;
	private int skipy;
	private int selecty;

	// Images
	private BufferedImage health;
	private BufferedImage powerup;
	private BufferedImage skip;

	// State for skip
	GameStateManager gsm;
	
	public InvConsume(GameStateManager gsm) {
		healthy = Center.aligny(11);
		powerupy = Center.aligny(19);
		skipy = Center.aligny(27);
		shieldy = Center.aligny(35);
		
		this.gsm = gsm;

		currentSelection = 1;
		
		try {
			health = ImageIO.read(getClass().getResourceAsStream("/Shop/medpack.png"));
			skip = ImageIO.read(getClass().getResourceAsStream("/Shop/skipIcon.png"));
			powerup = ImageIO.read(getClass().getResourceAsStream("/Icons/powerup.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {

		if(currentSelection == 1) selecty = healthy + 20;
		if(currentSelection == 2) selecty = powerupy + 20;
		if(currentSelection == 3) selecty = skipy + 20;
		if(currentSelection == 4) selecty = shieldy + 20;
		
	}
	private void buy() {
		if(currentSelection == 1 && Consumables.healths.size() > 0) {
			Consumables.healths.pop().addHealth();
		}
		if(currentSelection == 2 && Consumables.powerups.size() > 0) {
			Consumables.powerups.pop().addPowerup();
		}
		if(currentSelection == 3 && Consumables.skips.size() > 0) {
			Consumables.skips.pop().addSkip(gsm);
		}
		if(currentSelection == 4 && Consumables.shields.size() > 0) {
			AudioPlayer.playSound(SKeys.Shield_Activated);
			AudioPlayer.loopSound(SKeys.Shield_Running);
			Consumables.shields.pop().addShield();
		}
	}
	
	public void draw(Graphics2D g) {
		// Defaults
		g.setColor(Color.BLACK);

		// Draw health packs
		g.drawImage(health, Center.align(34), healthy, 25, 25, null);
		g.drawString("Health Pack              USE", Center.align(38), healthy + 17);
		// Draw quantity
		g.drawString(String.valueOf(Consumables.healths.size()) + " x", Center.align(58), healthy + 17);

		g.drawImage(powerup, Center.align(34), powerupy, 25, 25, null);
		g.drawString("Instant Powerup          USE", Center.align(38.2), powerupy + 17);
		// Draw quantity
		g.drawString(String.valueOf(Consumables.powerups.size()) + " x", Center.align(58), powerupy + 17);

		// Draw skips
		g.drawImage(skip, Center.align(34), skipy, 25, 25, null);
		g.drawString("Skip Level                 USE", Center.align(38.2), skipy + 17);
		// Draw quantity
		g.drawString(String.valueOf(Consumables.skips.size()) + " x", Center.align(58), skipy + 17);

		// Draw shields
		g.setColor(Color.CYAN);
		g.drawOval(Center.align(34), shieldy, 23, 23);
		g.setColor(Color.BLACK);
		g.drawString("Shield                         USE", Center.align(38), shieldy + 17);
		// Draw quantity
		g.drawString(String.valueOf(Consumables.shields.size()) + " x", Center.align(58), shieldy + 17);

		// Draw select
		g.setColor(Color.GREEN);
		if (Inventory.tab == 2)
			g.fillRect(Center.align(49.2), selecty, 26, 8);
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
		if(k == KeyEvent.VK_ENTER) {
			if(Inventory.tab == 2) buy();
		}
	}
	
	public void keyReleased(int k) {
		
	}
	
}
