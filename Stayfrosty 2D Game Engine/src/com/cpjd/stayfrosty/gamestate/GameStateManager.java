package com.cpjd.stayfrosty.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.cpjd.stayfrosty.audio.AudioLoad;
import com.cpjd.stayfrosty.levels.Cutscene1;
import com.cpjd.stayfrosty.levels.Lv1_1;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.menu.Credits;
import com.cpjd.stayfrosty.menu.Intro;
import com.cpjd.stayfrosty.menu.Menu;
import com.cpjd.stayfrosty.menu.PauseState;
import com.cpjd.tools.Layout;

/* Description
 *  Stores all of the different levels, menus, screens, etc. of the game
 * Use
 *  Adding a new state
 *   1) Add a new code under Game States Codes
 *   2) Increase NUMGAMESTATES by 1
 *   3) Add new <state> statement to loadState();
 *   4) Create the new state in com.cpjd.stayfrosty.states
 */
public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	// Game States Codes
	public static final int NUMGAMESTATES = 6;
	// Menu stuff
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int CREDITS = 2;
	// Levels
	public static final int L_TUTORIAL = 3;
	public static final int L1_1 = 4;
	public static final int L1_2 = 5;

	// Pause
	private PauseState pauseState;
	private boolean paused;

	// Loaidng
	BufferedImage background;

	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];

		try {
			background = ImageIO.read(getClass().getResource("/Backgrounds/wood.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pauseState = new PauseState(this);
		paused = false;

		currentState = INTRO;
		loadState(currentState);
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	private void loadState(int state) {
		if (state == INTRO)
			gameStates[state] = new Intro(this);
		if (state == MENU)
			gameStates[state] = new Menu(this);
		if (state == CREDITS)
			gameStates[state] = new Credits(this);
		if (state == L1_1)
			gameStates[state] = new Cutscene1(this);
		if (state == L1_2) gameStates[state] = new Lv1_1(this);

	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public int getState() {
		return currentState;
	}

	public void setPaused(boolean b) {
		paused = b;
	}

	public void update() {
		if (paused) {
			pauseState.update();
			return;
		}

		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	public void draw(Graphics2D g) {

		Color c = new Color(0f, 0f, 1f, .003f);
		g.setColor(c);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		if (paused) {
			pauseState.draw(g);
			return;
		}

		if (currentState != INTRO) {
			g.drawImage(background, 0, 0, null);
		}

		// Draw loading
		if (getState() <= GameStateManager.MENU) {

			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			double percent = AudioLoad.p / AudioLoad.TOTAL_ITEMS * 100;

			g.fillRect((int)Layout.centerw(200), (int)Layout.aligny(90), (int) percent * 2, 20);

			// Draw total
			g.setColor(Color.BLACK);
			g.drawRect((int)Layout.centerw(200) - 1, (int)Layout.aligny(90) - 1, 201, 21);

			// Draw loading
			g.drawString("Loading... ", (int)Layout.getStringCenter(0, GamePanel.WIDTH, "Loading...", g), (int)Layout.aligny(80));
		}

		if (gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}

		// Draw debug
		g.setColor(Color.RED);
		if (GamePanel.DEBUG)
			g.drawString("*", 5, 20);

	}

	public void keyPressed(int k) {
		if (GamePanel.DEBUG && k == KeyEvent.VK_F9) {
			System.gc();
		}
		if (paused) {
			pauseState.keyPressed(k);
			return;
		}
		if (GamePanel.DEBUG && k == KeyEvent.VK_L) {
			// AudioPlayer.stopAll();
			try {
				setState(Integer.parseInt(JOptionPane.showInputDialog("Enter the level code (lv + 4): ")));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (gameStates[currentState] != null) {
			gameStates[currentState].keyPressed(k);
		}
	}

	public void keyReleased(int k) {
		if (paused) {
			pauseState.keyReleased(k);
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].keyReleased(k);

	}

}
