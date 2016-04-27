package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

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

	private String motd;
	private Font font;
	private double y;
	
	public Intro(GameStateManager gsm) {
		super(gsm);

		if (!AudioLoad.finished) new AudioLoad();

		try {
			motd = MOTD.getMOTD();
			
			// Cats PJ logo
			logo = ImageIO.read(getClass().getResourceAsStream("/CPJD/cpjdlogo.png"));

			// Game engine logo
			engine = ImageIO.read(getClass().getResourceAsStream("/CPJD/engine.png"));
			
			InputStream inStream = getClass().getResourceAsStream("/Fonts/8-BIT WONDER.TTF");
			Font rawFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
			font = rawFont.deriveFont(10.0f);

		} catch (Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}

	}
	public void startMusic() {};
	public void update() {
		if (AudioLoad.finished) {
			gsm.setState(GameStateManager.MENU);
		}
		y += 0.04;

	}

	public void draw(Graphics2D g) {
		// Clear screen
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.drawImage(logo, Layout.getObjectCenter(0, GamePanel.WIDTH / 2, logo.getWidth() / 2), Layout.centerh(logo.getHeight() / 2),
				logo.getWidth() / 2, logo.getHeight() / 2, null);
		g.drawImage(engine, Layout.getObjectCenter(GamePanel.WIDTH / 2, GamePanel.WIDTH, engine.getWidth()), Layout.centerh(engine.getHeight()),
				null);

		// Draw loading
		if (gsm.getState() <= GameStateManager.MENU) {

			double percent = AudioLoad.p / AudioLoad.TOTAL_ITEMS * 100;

			g.setColor(GameStateManager.hud);
			g.fillRect((int) Layout.centerw(200), (int) Layout.aligny(90), (int) percent * 2, 20);

			// Draw total
			g.setColor(Color.BLACK);
			g.drawRect((int) Layout.centerw(200) - 1, (int) Layout.aligny(90) - 1, 201, 21);
			g.drawString(motd, 5, Layout.getStringHeight(g) + 5 + (int)y);

		}
	}

	public void keyPressed(int k) {

	}

	public void keyReleased(int k) {
	}
}
