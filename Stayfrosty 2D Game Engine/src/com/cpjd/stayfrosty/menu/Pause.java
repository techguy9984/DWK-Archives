package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Log;

public class Pause extends GameState {
	
	// State controllers
	private boolean paused;
	private long elapsed = 0;
	
	// Animation
	private int width;
	private int maxWidth = 200;
	BufferedImage background;
	
	// Font stuff
	private Font font;
	private String[] options = {"Resume","Options","Restart","Exit to menu"};
	private int currentSelection;
	
	public Pause(GameStateManager gsm) {
		super(gsm);
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/pause.png"));
			
			currentSelection = 0;
			
			InputStream inStream = getClass().getResourceAsStream("/Fonts/8-BIT WONDER.TTF");
			Font rawFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
			font = rawFont.deriveFont(10.0f);
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
		paused = false;
	}
	
	public void update() {

	}

	
	public void draw(Graphics2D g) {	
		g.drawImage(background, 0, 0, null);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, Layout.centerh(200), width, 200);
		
		if(width < maxWidth) width+=25;
		
		// Draw text
		g.setFont(font);
		for(int i = 0, j = 1; i < options.length; i++, j++) {
			if(currentSelection == i) {
				g.setColor(Color.WHITE);
			} else g.setColor(Color.BLACK);
			
			g.drawString(options[i], 5, Layout.aligny(25+(j * 10)));
		}
	}
	
	public void requestChange() {
		// Only acknowledge the request if a certain time period has passed (prevents spamming)
		if(System.currentTimeMillis() - elapsed > 175 && gsm.getState() >= GameStateManager.L1_2) {
			elapsed = System.currentTimeMillis();
			this.paused = !this.paused;
			// Reset everything
			if(this.paused == false) {
				width = 0;
			}
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			if(currentSelection == 0) requestChange();
			if(currentSelection == 2) {
				requestChange();
				gsm.setState(gsm.getState());
			}
			if(currentSelection == 3) {
				requestChange();
				gsm.setState(GameStateManager.MENU);
			}
		}
		
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if(currentSelection < options.length - 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection ++;
			}
		}
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if(currentSelection > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection--;
			}
		}
	}
	public void keyReleased(int k) {}
	
}
