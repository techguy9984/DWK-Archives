package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.cpjd.input.Keymap;
import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
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
	
	public Options() {
		reset();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(25, ypos, GamePanel.WIDTH - 50, GamePanel.HEIGHT - 50);
		
		g.setColor(Color.BLACK);
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
		g.drawString("Debug: false ", Layout.alignx(57), Layout.aligny(20) + ypos);
		g.drawString("Reset options", Layout.alignx(57), Layout.aligny(25) + ypos);
		
		// Rendering
		g.setColor(Color.BLACK);
		g.drawString("Rendering", Layout.alignx(5), Layout.aligny(50) + ypos);
		g.drawString("High quality: ", Layout.alignx(7), Layout.aligny(55) + ypos);
		g.drawString("Target FPS: ", Layout.alignx(7), Layout.aligny(60) + ypos);
		
		// Saving
		g.drawString("Game files", Layout.alignx(30), Layout.aligny(50) + ypos);
		g.setColor(Color.RED);
		g.drawString("Delete ALL save data", Layout.alignx(32), Layout.aligny(55) + ypos);
		
		// Draw version
		g.setColor(Color.BLACK);
		g.drawString(GamePanel.VERSION, Layout.alignx(88), Layout.aligny(93));
		
		// Confirm and exit
		g.setColor(Color.RED);
		g.drawString("ESC to exit", Layout.alignx(85), Layout.aligny(5) + ypos);
		
		// Draw select box
		g.setColor(Color.ORANGE);
		if(waiting) g.setColor(Color.GREEN);
		if(currentSelection < controlsString.length) g.fillOval(Layout.alignx(4.4), Layout.aligny(((currentSelection + 1) * 5) + 12) + ypos, 12, 12);
		else g.fillOval(Layout.alignx(29.4), Layout.aligny(((currentSelection - 3) * 5) + 12) + ypos, 12, 12);
	}
	
	public void update() {
		handleInput();
		
		if(ypos < 25) {
			ypos += 25;
		}
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
			if(currentSelection == 4) {
				AudioPlayer.playSound(SKeys.Change);
				AudioPlayer.muteSFX = !AudioPlayer.muteSFX;
			}
			if(currentSelection == 5) {
				AudioPlayer.playSound(SKeys.Change);
				AudioPlayer.muteMusic = !AudioPlayer.muteMusic;
				if(AudioPlayer.muteMusic) AudioLoad.stopAll();
			}
		}
		// Manage changing currentSelection
		else if(k == Keymap.keymap[Keymap.right]) {
			AudioPlayer.playSound(SKeys.Change);
			if(currentSelection == 0) currentSelection = 4;
			if(currentSelection == 1) currentSelection = 5;
			if(currentSelection == 2) currentSelection = 5;
			if(currentSelection == 3) currentSelection = 5;
			
		}
		else if(k == Keymap.keymap[Keymap.left]) {
			AudioPlayer.playSound(SKeys.Change);
			if(currentSelection == 4) currentSelection = 0;
			if(currentSelection == 5) currentSelection = 1;
		}
		else if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if(currentSelection > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection--;
			}
		}
		else if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if(currentSelection < controlsString.length + 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection++;
			}
		}
	}

}
