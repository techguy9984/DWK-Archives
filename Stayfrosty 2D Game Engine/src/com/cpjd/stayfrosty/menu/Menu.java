package com.cpjd.stayfrosty.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.lwjgl.openal.AL;

import com.cpjd.smartui.SmartButton;
import com.cpjd.smartui.SmartButtonGroup;
import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
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
	
	// UI
	SmartButton play, loadButton, settings, stats, exit;
	SmartButtonGroup buttons;
	
	public Menu(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		// Set up buttons
		play = new SmartButton("/UI/create.png","/UI/createClicked.png");
		play.setBulge(true);
		
		loadButton = new SmartButton("/UI/loadFrames.png", "/UI/loadClicked.png",52,53,40);
		loadButton.setBulge(true);
		
		settings = new SmartButton("/UI/options.png","/UI/optionsClicked.png");
		settings.setBulge(true);
		settings.setRotate(true);
		
		stats = new SmartButton("/UI/leaderboard.png","/UI/leaderboardClicked.png");
		stats.setBulge(true);
		
		exit = new SmartButton("/UI/exit.png","/UI/exitClicked.png");
		exit.setBulge(true);
		
		SmartButton[] list = new SmartButton[5];
		list[0] = play; list[1] = loadButton; list[2] = settings; list[3] = stats; list[4] = exit;
		buttons = new SmartButtonGroup("/UI/play.png","/UI/playClicked.png",list,GamePanel.WIDTH,GamePanel.HEIGHT);
		
		if(!AudioLoad.finished) AudioLoad.Start();
		
		AudioPlayer.loopMusic(SKeys.Menu_Music);

		// Load the images
		try {
			background = new Background("/Backgrounds/back_cave.png", 1);
			background.setVector(-1,0);
			
			titleImage = ImageIO.read(getClass().getResourceAsStream("/CPJD/title.png"));

			selectImage = ImageIO.read(getClass().getResourceAsStream("/Interface/select.png"));
		} catch(Exception e) {
			Error.error(e,Error.IO_IMAGE_ERROR);
		}
		
	}

	public void update() {
		background.update();
		
		buttons.update();
		
		if(buttons.isClicked(0)) {
			gsm.setState(load.getCurrentLevel());
			GamePanel.targetTime = 1000 / 60;
		}
		if(buttons.isClicked(4)) {
			AL.destroy();
			System.exit(0);
		}
	}
	
	public void draw(Graphics2D g) { 
		// Draw the background
		background.draw(g);

		// Draw the title
		//g.drawImage(titleImage, Center.centeri(titleImage.getWidth() / 4), 30, titleImage.getWidth() / 4, titleImage.getHeight() / 4, null);
		buttons.draw(g);
		
	}
	
	public void keyPressed(int k) {

	}
	public void keyReleased(int k) {
		PauseState.keyLock = false;
	}
}
