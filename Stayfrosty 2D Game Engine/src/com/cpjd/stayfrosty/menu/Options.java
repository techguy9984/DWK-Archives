package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.cpjd.input.Keymap;
import com.cpjd.input.Keys;
import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Layout;

public class Options {
	
	// Position of background
	private int ypos;
	
	private int currentSelection;
	
	private boolean waiting;
	
	private String[] controlsString = {
			"Walk right: ",
			"Walk left: ",
			"Jump: ",
			"Select: "
	};
	
	private String[] keyText = new String[4];
	
	GameStateManager gsm;
	
	ColorPicker cp;
	
	public Options(GameStateManager gsm) {
		this.gsm = gsm;
		
		cp = new ColorPicker();
		
		reset();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(GameStateManager.hud.getRed(), (int)(GameStateManager.hud.getBlue() + 30), GameStateManager.hud.getGreen()));
		g.fillRect(25, ypos, GamePanel.WIDTH - 50, GamePanel.HEIGHT - 50);
		
		g.setColor(new Color(255 - GameStateManager.hud.getRed(), 255 - GameStateManager.hud.getBlue(), 255 - GameStateManager.hud.getGreen()));
		g.drawString("Options",Layout.alignx(5), Layout.aligny(5) + ypos);
		
		// Draw control labels
		g.drawString("Controls", Layout.alignx(5), Layout.aligny(15) + ypos);
		g.drawString(controlsString[0]+keyText[0], Layout.alignx(7), Layout.aligny(20) + ypos);
		g.drawString(controlsString[1]+keyText[1], Layout.alignx(7), Layout.aligny(25) + ypos);
		g.drawString(controlsString[2]+keyText[2], Layout.alignx(7), Layout.aligny(30) + ypos);
		g.drawString(controlsString[3]+keyText[3], Layout.alignx(7), Layout.aligny(35) + ypos);
		
		// Sound
		g.drawString("Sounds ", Layout.alignx(30), Layout.aligny(15) + ypos);
		g.drawString("Mute Sfx: "+AudioPlayer.muteSFX, Layout.alignx(32), Layout.aligny(20) + ypos);
		g.drawString("Mute Music: "+AudioPlayer.muteMusic, Layout.alignx(32), Layout.aligny(25) + ypos);
		
		// Other
		g.drawString("Other ", Layout.alignx(55), Layout.aligny(15) + ypos);
		g.drawString("Debug: "+GamePanel.DEBUG, Layout.alignx(57), Layout.aligny(20) + ypos);
		g.drawString("Reset options", Layout.alignx(57), Layout.aligny(25) + ypos);
		
		// Rendering
		g.setColor(new Color(255 - GameStateManager.hud.getRed(), 255 - GameStateManager.hud.getBlue(), 255 - GameStateManager.hud.getGreen()));
		g.drawString("Rendering", Layout.alignx(5), Layout.aligny(50) + ypos);
		g.drawString("High quality: "+GamePanel.QUALITY, Layout.alignx(7), Layout.aligny(55) + ypos);
		g.drawString("HUD Color: Default", Layout.alignx(7), Layout.aligny(60) + ypos);
		
		// Saving
		g.drawString("Game files", Layout.alignx(30), Layout.aligny(50) + ypos);
		g.setColor(Color.RED);
		g.drawString("Delete ALL save data", Layout.alignx(32), Layout.aligny(55) + ypos);
		
		// Draw version
		g.setColor(new Color(255 - GameStateManager.hud.getRed(), 255 - GameStateManager.hud.getBlue(), 255 - GameStateManager.hud.getGreen()));
		g.drawString(GamePanel.VERSION, Layout.alignx(88), Layout.aligny(93));
		
		// Confirm and exit
		g.setColor(Color.RED);
		g.drawString("ESC to exit", Layout.alignx(85), Layout.aligny(5) + ypos);
		
		// Draw select box
		drawSelect(g);
		
		// Draw color picker
		cp.draw(g);
	}
	
	private void drawSelect(Graphics2D g) {
		g.setColor(Color.ORANGE);
		if(waiting) g.setColor(Color.GREEN);
		
		// Choose where to draw it
		if(currentSelection < controlsString.length) g.fillOval(Layout.alignx(4.4), Layout.aligny(((currentSelection + 1) * 5) + 12) + ypos, 12, 12); // controls
		else if(currentSelection >= controlsString.length && currentSelection < 6) g.fillOval(Layout.alignx(4.4), Layout.aligny(((currentSelection + 4) * 5) + 12) + ypos, 12, 12); // rendering
		else if(currentSelection >= 5 && currentSelection < 8) g.fillOval(Layout.alignx(29.4), Layout.aligny(((currentSelection - 5) * 5) + 12) + ypos, 12, 12); // sounds
		else if(currentSelection == 8) g.fillOval(Layout.alignx(29.4), Layout.aligny(((currentSelection) * 5) + 12) + ypos, 12, 12); // game files
		else g.fillOval(Layout.alignx(54.4), Layout.aligny(((currentSelection - 8) * 5) + 12) + ypos, 12, 12); // other
			
		
	}
	
	public void update() {
		handleInput();
		
		if(ypos < 25) {
			ypos += 25;
		}
		
		cp.update();
	}
	
	public void reloadIds() {
		keyText[0] = Keymap.getKeyText(Keymap.right);
		keyText[1] = Keymap.getKeyText(Keymap.left);
		keyText[2] = Keymap.getKeyText(Keymap.jump);
		keyText[3] = Keymap.getKeyText(Keymap.select);
	}
	
	public void reset() {
		waiting = false;
		
		reloadIds();
		
		ypos = -GamePanel.HEIGHT;
		currentSelection = 0;
	}
	
	public void handleInput() {

	}
	public void keyPressed(int k) {
		if(k == Keymap.keymap[Keymap.pause]) {
			if(cp.isActive()) cp.requestChange();
		}
		
		// Waiting for a key press update for a key mapping
		if(waiting) {
			if(currentSelection == 0) {
				AudioPlayer.playSound(SKeys.Change);
				Keymap.setKey(Keymap.right, k);
				keyText[0] = Keymap.getKeyText(Keymap.right);
				reloadIds();
				waiting = false;
			}
			if(currentSelection == 1) {
				AudioPlayer.playSound(SKeys.Change);
				Keymap.setKey(Keymap.left, k);
				keyText[0] = Keymap.getKeyText(Keymap.left);
				reloadIds();
				waiting = false;
			}
			if(currentSelection == 2) {
				AudioPlayer.playSound(SKeys.Change);
				Keymap.setKey(Keymap.jump, k);
				keyText[0] = Keymap.getKeyText(Keymap.jump);
				reloadIds();
				waiting = false;
			}
			if(currentSelection == 3) {
				AudioPlayer.playSound(SKeys.Change);
				Keymap.setKey(Keymap.select, k);
				keyText[0] = Keymap.getKeyText(Keymap.select);
				reloadIds();
				waiting = false;
			}
		}
		
		// If the user selects any of the items
		else if(k == Keymap.keymap[Keymap.select]) {
			if(currentSelection < controlsString.length) {
				AudioPlayer.playSound(SKeys.Change);
				keyText[currentSelection] = "Press a key...";
				waiting = true;
			}
			// Quality
			if(currentSelection == 4) {
				GamePanel.QUALITY = !GamePanel.QUALITY;
			}
			// Color
			if(currentSelection == 5) {
				cp.requestChange();
			}
			// SFX
			if(currentSelection == 6) {
				AudioPlayer.playSound(SKeys.Change);
				AudioPlayer.muteSFX = !AudioPlayer.muteSFX;
			}
			// Music
			if(currentSelection == 7) {
				AudioPlayer.playSound(SKeys.Change);
				AudioPlayer.muteMusic = !AudioPlayer.muteMusic;
				if(AudioPlayer.muteMusic) AudioLoad.stopAll();
				if(!AudioPlayer.muteMusic) {
					gsm.restartMusic();
				}
			}
			// Debug
			if(currentSelection == 9) {
				GamePanel.DEBUG = !GamePanel.DEBUG;
			}
			// Reset options
			if(currentSelection == 10) {
				Keymap.setKey(Keymap.right, KeyEvent.VK_D);
				Keymap.setKey(Keymap.left, KeyEvent.VK_A);
				Keymap.setKey(Keymap.jump, KeyEvent.VK_SPACE);
				Keymap.setKey(Keymap.select, KeyEvent.VK_ENTER);
				GamePanel.QUALITY = true;
				AudioPlayer.muteMusic = false;
				AudioPlayer.muteSFX = false;
				GamePanel.DEBUG = false;
			}
		}
		// Manage changing currentSelection
		else if(k == Keymap.keymap[Keymap.right]) {
			AudioPlayer.playSound(SKeys.Change);
			manageSpecialCaseRight();
			
		}
		else if(k == Keymap.keymap[Keymap.left]) {
			AudioPlayer.playSound(SKeys.Change);
			manageSpecialCaseLeft();
		}
		else if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if(currentSelection > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection--;
			}
		}
		else if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if(currentSelection < 10) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection++;
			}
		}
	}
	
	private void manageSpecialCaseRight() {
		switch(currentSelection) {
			case 0: currentSelection = 6; break;
			case 1: currentSelection = 7; break;
			case 2: currentSelection = 7; break;
			case 3: currentSelection = 7; break;
			case 4: currentSelection = 8; break;
			case 5: currentSelection = 8; break;
			case 6: currentSelection = 9; break;
			case 7: currentSelection = 10; break;
			case 8: currentSelection = 10; break;
		}
	}
	private void manageSpecialCaseLeft() {
		switch(currentSelection) {
			case 6: currentSelection = 0; break;
			case 7: currentSelection = 1; break;
			case 8: currentSelection = 4; break;
			case 9: currentSelection = 6; break;
			case 10: currentSelection = 7; break;
		}
	}

}
