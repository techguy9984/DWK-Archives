package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.cpjd.input.Keymap;
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
		g.drawString("Sfx: ", Layout.alignx(32), Layout.aligny(20) + ypos);
		g.drawString("Music: ", Layout.alignx(32), Layout.aligny(25) + ypos);
		
		// Confirm and exit
		g.setColor(Color.RED);
		g.drawString("ESC to exit", Layout.alignx(85), Layout.aligny(5) + ypos);
		
		// Draw select box
		g.setColor(Color.ORANGE);
		if(waiting) g.setColor(Color.GREEN);
		g.fillOval(Layout.alignx(4.4), Layout.aligny(((currentSelection + 1) * 5) + 12) + ypos, 12, 12);
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
		if(waiting) {
			if(currentSelection == 0) {
				Keymap.setKey(Keymap.right, k);
				keyText[0] = Keymap.getKeyText(Keymap.right);
				reloadIds();
				waiting = false;
			}
			if(currentSelection == 1) {
				Keymap.setKey(Keymap.left, k);
				keyText[0] = Keymap.getKeyText(Keymap.left);
				reloadIds();
				waiting = false;
			}
			if(currentSelection == 2) {
				Keymap.setKey(Keymap.jump, k);
				keyText[0] = Keymap.getKeyText(Keymap.jump);
				reloadIds();
				waiting = false;
			}
			if(currentSelection == 3) {
				Keymap.setKey(Keymap.select, k);
				keyText[0] = Keymap.getKeyText(Keymap.select);
				reloadIds();
				waiting = false;
			}
		}
		
		else if(k == Keymap.keymap[Keymap.select] && currentSelection < 5) {
			keyText[currentSelection] = "Press a key...";
			waiting = true;

		}
		else if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if(currentSelection > 0) {
				currentSelection--;
			}
		}
		else if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if(currentSelection < controlsString.length) {
				currentSelection++;
			}
		}
	}

}
