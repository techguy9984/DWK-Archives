package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Log;

/* Description
 *  Loading screen on application startup
 */
public class Intro extends GameState {
	
	// CPJD Logo
	private BufferedImage logo;

	// Stayfrosty Engine Logo
	private BufferedImage engine;

	public static final boolean SKIP_ALLOWED = true;
	
	public Intro(GameStateManager gsm) {
		super(gsm);
		
		if(!AudioLoad.finished) new AudioLoad();
		
		try {
			// Cats PJ logo
			logo = ImageIO.read(getClass().getResourceAsStream("/CPJD/cpjdlogo.png"));
			
			// Game engine logo
			engine = ImageIO.read(getClass().getResourceAsStream("/CPJD/engine.png"));
			
			
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
	}

	public void update() {
		if(AudioLoad.finished) {
			gsm.setState(GameStateManager.MENU);
		}
	}

	
	public void draw(Graphics2D g) {
		// Clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.drawImage(logo, Layout.getObjectCenter(0, GamePanel.WIDTH / 2, logo.getWidth() / 2), Layout.centerh(logo.getHeight() / 2),
				logo.getWidth() / 2, logo.getHeight() / 2, null);
		g.drawImage(engine, Layout.getObjectCenter(GamePanel.WIDTH / 2, GamePanel.WIDTH, engine.getWidth()), Layout.centerh(engine.getHeight()),
				null);

		// Draw loading
		if (gsm.getState() <= GameStateManager.MENU) {

			double percent = AudioLoad.p / AudioLoad.TOTAL_ITEMS * 100;

			g.setColor(Color.DARK_GRAY);
			g.fillRect((int) Layout.centerw(200), (int) Layout.aligny(90), (int) percent * 2, 20);

			// Draw total
			g.setColor(Color.BLACK);
			g.drawRect((int) Layout.centerw(200) - 1, (int) Layout.aligny(90) - 1, 201, 21);

		}

	}
	
	public void keyPressed(int k) {}
	public void keyReleased(int k) {}
}
