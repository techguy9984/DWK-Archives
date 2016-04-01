package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.openal.AL;

import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Tutorial;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.Background;
import com.cpjd.stayfrosty.util.Center;
import com.cpjd.stayfrosty.util.Error;
/* Description
 *  The menu for accessing all our game stuff
 *  Runs under the the startup thread
 */
public class Menu extends GameState {

	// Images
	private BufferedImage selectImage;
	private BufferedImage titleImage;
	private Background background;
	
	// Options
	private String[] options = {
			"Continue","Credits","Quit"
	};
	
	// Aesthetics
	private Font optionsFont;
	private int vertStart; // The y position of the first options item
	private int pad; // Space between options
	
	// Technical variables
	private int currentChoice = 0; // The option that is currently selected

	Tutorial t;
	
	Random r;
	
	String toDisplay;
	
	BufferedImage bryan;
	
	private int bx;
	private int by;
	private int xinc;
	private int yinc;
	
	
	public Menu(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		if(!AudioLoad.finished) AudioLoad.Start();
		
		AudioPlayer.loopMusic(SKeys.Menu_Music);
		
		optionsFont = new Font("Comic Sans MS",Font.PLAIN,15);
		
		if(load.getCurrentLevel() == GameStateManager.L_TUTORIAL) options[0] = "Start new game";
		
		bx = 0;
		by = 0;
		xinc = 2;
		yinc = 2;
		
		vertStart = Center.aligny(35);
		pad = 10; // In percent
		
		toDisplay = "";
		
		r = new Random();
		
		t = new Tutorial();
		toDisplay = t.getMOTD(r.nextInt(4));

		
		// Load the images
		try {
			background = new Background("/Backgrounds/back_cave.png", 1);
			background.setVector(-1,0);
			
			titleImage = ImageIO.read(getClass().getResourceAsStream("/CPJD/title.png"));
			
			bryan = ImageIO.read(getClass().getResourceAsStream("/MLG/pic.png"));
			
			selectImage = ImageIO.read(getClass().getResourceAsStream("/Interface/select.png"));
		} catch(Exception e) {
			Error.error(e,Error.IO_IMAGE_ERROR);
		}
		
	}

	public void update() {
		background.update();
		
		
		
		// Update bryan
		bx += xinc;
		by += yinc;
		
		if(bx < 0) xinc = 2;
		if(bx > GamePanel.WIDTH - 128) xinc = -2;
		if(by < 0) yinc = 2;
		if(by > GamePanel.HEIGHT - 128) yinc = -2;
	}
	
	public void draw(Graphics2D g) { 
		// Draw the background
		background.draw(g);
		
		// Draw bryan
		g.drawImage(bryan, bx, by, null);
		
		// Draw the title
		g.drawImage(titleImage, Center.centeri(titleImage.getWidth() / 4), 30, titleImage.getWidth() / 4, titleImage.getHeight() / 4, null);
		// Draw the options
		for(int i = 0, j = 1; i < options.length; i++) {
			g.setFont(optionsFont);
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
				// Draw the selection image next to the currently selected option
				g.drawImage(selectImage, Center.center(g, options[i]) -  10, vertStart + Center.aligny(j * pad) - 7, -selectImage.getWidth()
						/ 2, selectImage.getHeight() / 2, null);
			}
			else {
				g.setColor(Color.RED);
			}
			
			g.drawString(options[i], Center.center(g, options[i]), vertStart + Center.aligny(j * pad));
			j++;
		}
		
		// Draw motd
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.drawString(toDisplay, Center.center(g, toDisplay), Center.aligny(20));
		
		/* Draw version
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Arial",Font.PLAIN,5));
		g.drawString(Game.version, GamePanel.WIDTH - Center.getSWidth(g, Game.version) - 2, 
				GamePanel.HEIGHT - Center.getSHeight(g));*/
		
	}
	
	private void select() {
		AudioLoad.stopAll();
		if(currentChoice == 0) { // Play
			AudioPlayer.playSound(SKeys.Select);
			gsm.setState(load.getCurrentLevel());
			//gsm.setState(GameStateManager.LEVEL_1);
		}
		if(currentChoice == 1) { // Credits
			AudioPlayer.playSound(SKeys.Select);
			gsm.setState(GameStateManager.CREDITS);
		}
		if(currentChoice == 2) { // Quit
			AudioPlayer.playSound(SKeys.Select);
			AL.destroy();
			System.exit(0);
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER && !PauseState.keyLock) select();
		if(k == KeyEvent.VK_DOWN) {
			if(currentChoice < options.length - 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentChoice++;
			}
		}
		if(k == KeyEvent.VK_UP) {
			if(currentChoice > 0) {
				AudioPlayer.playSound(SKeys.Change);
				currentChoice--;
			}
		}
	}
	public void keyReleased(int k) {
		PauseState.keyLock = false;
	}
}
