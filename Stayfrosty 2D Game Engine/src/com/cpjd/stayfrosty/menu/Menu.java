package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.openal.AL;

import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.Background;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Log;
/* Description
 *  The menu for accessing all our game stuff
 *  Runs under the the startup thread
 */
public class Menu extends GameState {

	// Images
	private BufferedImage titleImage;
	private Background background;
	private BufferedImage ilum;
	Random r;
	private boolean display;
	
	private Font font;
	
	// Menu
	private String[] options = {
			"Play","Credits","Quit"
	};
	
	private int currentSelection;
	
	public Menu(GameStateManager gsm) {
		super(gsm);
		
		if(!AudioLoad.finished) AudioLoad.Start();
		
		AudioPlayer.loopMusic(SKeys.Theme);

		currentSelection = 0;
		
		display = false;
		r = new Random();
		
		// Load the images
		try {
			background = new Background("/Backgrounds/back_cave.png", 1);
			background.setVector(-1,0);
			
			ilum = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/illuminati.png"));
			
			titleImage = ImageIO.read(getClass().getResourceAsStream("/CPJD/title.png"));
			
			InputStream inStream = getClass().getResourceAsStream("/Fonts/8-BIT WONDER.TTF");
			Font rawFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
			font = rawFont.deriveFont(20.0f);
			
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
	}

	public void update() {
		background.update();
		
		// Manage creepy effect
		if(r.nextInt(60 * 120) < 1 && !display) {
			display = true;
			AudioPlayer.playSound(SKeys.Creepy);
		}
		if(r.nextInt(60 * 8) < 1 && display) {
			AudioPlayer.stopSound(SKeys.Creepy);
			display = false;
		}
	}
	
	public void draw(Graphics2D g) {
		// Get font metrics
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		
		// Draw the background
		background.draw(g);

		// Draw the title
		g.drawImage(titleImage, (int)Layout.centerw(titleImage.getWidth() / 4), 30, titleImage.getWidth() / 4, titleImage.getHeight() / 4, null);
		
		// Illuminati
		if(display) g.drawImage(ilum, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		// Draw options
		for(int i = 0; i < options.length; i++) {
			g.setColor(Color.WHITE);
			if(currentSelection == i) {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], Layout.centerw(fm.stringWidth(options[i])), Layout.aligny((i + 4) * 10));
			//
			
		}

	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if(currentSelection > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection--;
			}
		}
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if(currentSelection < options.length - 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection++;
			}
		}
		if(k == KeyEvent.VK_ENTER) {
			if(currentSelection == 0) gsm.setState(GameStateManager.L1_1);
			if(currentSelection == 1) gsm.setState(GameStateManager.CREDITS);
			if(currentSelection == 2) {
				AL.destroy();
				System.exit(0);
			}
		}
		if(display && k == KeyEvent.VK_SPACE) {
			display = false;
			AudioPlayer.stopSound(SKeys.Creepy);
		}
	}
	public void keyReleased(int k) {
		PauseState.keyLock = false;
	}
}
