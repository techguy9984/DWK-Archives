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
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.files.Directory;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.files.SaveAnim;
import com.cpjd.stayfrosty.levels.Lv1_1;
import com.cpjd.stayfrosty.levels.Lv1_2;
import com.cpjd.stayfrosty.levels.Lv1_3;
import com.cpjd.stayfrosty.levels.Lv1_4;
import com.cpjd.stayfrosty.levels.Lv1_5;
import com.cpjd.stayfrosty.levels.Lv1_6;
import com.cpjd.stayfrosty.levels.Lv1_7;
import com.cpjd.stayfrosty.levels.Lv1_8;
import com.cpjd.stayfrosty.levels.Lv1_9;
import com.cpjd.stayfrosty.levels.Lv1_Boss;
import com.cpjd.stayfrosty.levels.Lv2_1;
import com.cpjd.stayfrosty.levels.Lv2_2;
import com.cpjd.stayfrosty.levels.Lv2_3;
import com.cpjd.stayfrosty.levels.Lv2_4;
import com.cpjd.stayfrosty.levels.Lv2_5;
import com.cpjd.stayfrosty.levels.Lv2_6;
import com.cpjd.stayfrosty.levels.Lv2_7;
import com.cpjd.stayfrosty.levels.Lv2_8;
import com.cpjd.stayfrosty.levels.Lv2_9;
import com.cpjd.stayfrosty.levels.Lv2_Boss;
import com.cpjd.stayfrosty.levels.Lv3_1;
import com.cpjd.stayfrosty.levels.Lv3_2;
import com.cpjd.stayfrosty.levels.Lv3_3;
import com.cpjd.stayfrosty.levels.Lv3_4;
import com.cpjd.stayfrosty.levels.Lv3_5;
import com.cpjd.stayfrosty.levels.Lv3_6;
import com.cpjd.stayfrosty.levels.Lv3_7;
import com.cpjd.stayfrosty.levels.Lv3_8;
import com.cpjd.stayfrosty.levels.Lv3_9;
import com.cpjd.stayfrosty.levels.Lv3_Boss;
import com.cpjd.stayfrosty.levels.Lv_Tutorial;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.menu.Credits;
import com.cpjd.stayfrosty.menu.Intro;
import com.cpjd.stayfrosty.menu.Menu;
import com.cpjd.stayfrosty.menu.PauseState;
import com.cpjd.stayfrosty.shop.Inventory;
import com.cpjd.stayfrosty.shop.Shop;
import com.cpjd.stayfrosty.util.Center;

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
	
	// Global state
	public static int globalState;
	
	
	// Entry point
	public static int entry;
	
	// Game States Codes
	public static final int NUMGAMESTATES = 41;
	// Menu stuff
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int CREDITS = 2;
	// Levels
	public static final int L_TUTORIAL = 5;
	public static final int L1_1 = 5;
	public static final int L1_2 = 6;
	public static final int L1_3 = 7;
	public static final int L1_4 = 8;
	public static final int L1_5 = 9;
	public static final int L1_6 = 10;
	public static final int L1_7 = 11;
	public static final int L1_8 = 12;
	public static final int L1_9 = 13;
	public static final int L1_BOSS = 14;	
	public static final int L2_1 = 16;
	public static final int L2_2 = 17;
	public static final int L2_3 = 18;
	public static final int L2_4 = 19;
	public static final int L2_5 = 20;
	public static final int L2_6 = 21;
	public static final int L2_7 = 22;
	public static final int L2_8 = 23;
	public static final int L2_9 = 24;
	public static final int L2_BOSS = 25;
	public static final int L3_1 = 27;
	public static final int L3_2 = 28;
	public static final int L3_3 = 29;
	public static final int L3_4 = 30;
	public static final int L3_5 = 31;
	public static final int L3_6 = 32;
	public static final int L3_7 = 33;
	public static final int L3_8 = 34;
	public static final int L3_9 = 35;
	public static final int L3_BOSS = 36;
	public static final int L1_Bonus = 38;
	public static final int L2_Bonus = 39;
	public static final int L3_Bonus = 40;
	
	public static boolean shopActive;
	public static boolean invActive;

	// Game state exclusive classes
	Shop shop;
	Inventory inv;
	
	// Pause
	private PauseState pauseState;
	private boolean paused;
	
	// Loading & Saving
	Load load;
	Save save;
	
	SaveAnim sa;
	
	// Loaidng
	BufferedImage background;
	
	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];
		
		try {
			background = ImageIO.read(getClass().getResource("/Backgrounds/wood.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// File loading
		Directory.setPath();
		
		sa = new SaveAnim();
		
		load = new Load();
		
		if(Directory.delete) {
			load.reload();
			Directory.delete = false;
		}
		
		save = new Save(load);
		
		entry = load.getCurrentLevel();
		
		pauseState = new PauseState(this, load, save);
		paused = false;
		
		shop = new Shop(this, load, save);
		inv = new Inventory(this,load,save);
		shopActive = false;
		invActive = false;

		currentState = INTRO;
		globalState = currentState;
		loadState(currentState);
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		globalState = state;
		pauseState.setRestarting(false);
		Inventory.equip = -1;
	}
	
	private void loadState(int state) {
		if(state == INTRO) gameStates[state] = new Intro(this, load, save);
		if(state == MENU) gameStates[state] = new Menu(this, load, save);
		if(state == CREDITS) gameStates[state] = new Credits(this, load, save);
		if(state == L_TUTORIAL) gameStates[state] = new Lv_Tutorial(this, load, save);
		if(state == L1_1) gameStates[state] = new Lv1_1(this, load,save);
		if(state == L1_2) gameStates[state] = new Lv1_2(this, load,save);
		if(state == L1_3) gameStates[state] = new Lv1_3(this, load,save);
		if(state == L1_4) gameStates[state] = new Lv1_4(this, load,save);
		if(state == L1_5) gameStates[state] = new Lv1_5(this, load,save);
		if(state == L1_6) gameStates[state] = new Lv1_6(this, load,save);
		if(state == L1_7) gameStates[state] = new Lv1_7(this, load,save);
		if(state == L1_8) gameStates[state] = new Lv1_8(this, load,save);
		if(state == L1_9) gameStates[state] = new Lv1_9(this, load,save);
		if(state == L1_BOSS) gameStates[state] = new Lv1_Boss(this, load,save);
		if(state == L2_1) gameStates[state] = new Lv2_1(this, load,save);
		if(state == L2_2) gameStates[state] = new Lv2_2(this, load,save);
		if(state == L2_3) gameStates[state] = new Lv2_3(this, load,save);
		if(state == L2_4) gameStates[state] = new Lv2_4(this, load,save);
		if(state == L2_5) gameStates[state] = new Lv2_5(this, load,save);
		if(state == L2_6) gameStates[state] = new Lv2_6(this, load,save);
		if(state == L2_7) gameStates[state] = new Lv2_7(this, load,save);
		if(state == L2_8) gameStates[state] = new Lv2_8(this, load,save);
		if(state == L2_9) gameStates[state] = new Lv2_9(this, load,save);
		if(state == L2_BOSS) gameStates[state] = new Lv2_Boss(this, load,save);
		if(state == L3_1) gameStates[state] = new Lv3_1(this, load,save);
		if(state == L3_2) gameStates[state] = new Lv3_2(this, load,save);
		if(state == L3_3) gameStates[state] = new Lv3_3(this, load,save);
		if(state == L3_4) gameStates[state] = new Lv3_4(this, load,save);
		if(state == L3_5) gameStates[state] = new Lv3_5(this, load,save);
		if(state == L3_6) gameStates[state] = new Lv3_6(this, load,save);
		if(state == L3_7) gameStates[state] = new Lv3_7(this, load,save);
		if(state == L3_8) gameStates[state] = new Lv3_8(this, load,save);
		if(state == L3_9) gameStates[state] = new Lv3_9(this, load,save);
		if(state == L3_BOSS) gameStates[state] = new Lv3_Boss(this, load,save);

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
		// Check if the player is dead
		if(Player.globalDead) {
			pauseState.reset();
			Player.globalDead = false;
		}
		
		sa.update();
		if(paused) { 
			pauseState.update();
			return;
		}
		if(Player.completedLevel) {
			AudioLoad.stopAll();
			AudioPlayer.playSound(SKeys.Level_Complete);
			int temp = getState();
			temp++;
			setState(temp);
			Player.completedLevel = false;
		}
		
		if(gameStates[currentState] != null && !shopActive && !invActive) gameStates[currentState].update();
		if(shopActive) shop.update();
		if(invActive) inv.update();
	}
	
	public void draw(Graphics2D g) {


		
		Color c = new Color(0f,0f,1f,.003f );
		g.setColor(c);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		

		if(paused) {
			pauseState.draw(g);
			return;
		}
		
		if (currentState != INTRO) {
			g.drawImage(background, 0, 0, null);
		}

		// Draw loading
		if(getState() <= GameStateManager.MENU) {
			
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial",Font.BOLD,15));
			double percent = AudioLoad.p /  32 * 100;
			
			g.fillRect(Center.centeri(200), Center.aligny(90), (int)percent * 2, 20);
			
			// Draw total
			g.setColor(Color.BLACK);
			g.drawRect(Center.centeri(200) - 1, Center.aligny(90) - 1, 201, 21);
			
			// Draw loading 
			g.drawString("Loading... ", Center.center(g, "Loading..."), Center.aligny(80));
		}
		
		if(gameStates[currentState] != null && !shopActive && !invActive) {
			gameStates[currentState].draw(g);
		}
		if(shopActive) shop.draw(g);
		if(invActive) inv.draw(g);
		sa.draw(g);
		
	}
	public void keyPressed(int k) {
		if(GamePanel.debug && k == KeyEvent.VK_F9) {
			System.gc();
		}
		if(paused) {
			pauseState.keyPressed(k);
			return;
		}
		// If in debug mode and freecaming, pressing this keys will turn it off
		if(k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_UP || k == KeyEvent.VK_SPACE) {
			Player.freecam = false;
		}
		
		if(k == KeyEvent.VK_O && currentState >= L_TUTORIAL && !invActive) {
			if(!shopActive) {
				shopActive = true;
				shop.init();
				return;
			}
			if(shopActive) {
				shop.resetTransition();
				shopActive = false;
				return;
			}
		}
		if(k == KeyEvent.VK_I && currentState >= L_TUTORIAL && !shopActive) {
			if(!invActive) {
				invActive = true;
				inv.init();
				return;
			}
			if(invActive) {
				inv.resetTransition();
				invActive = false;
				return;
			}
		}

		if(GamePanel.debug && k == KeyEvent.VK_U) {
			try {
				Player.currentMemes = Integer.parseInt(JOptionPane.showInputDialog("Enter the memes: "));
				
			} catch(Exception e) {
				
			}
		}
		
		if(GamePanel.debug && k == KeyEvent.VK_L) {
			//AudioPlayer.stopAll();
			try {
				setState(Integer.parseInt(JOptionPane.showInputDialog("Enter the level code (lv + 4): ")));
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(gameStates[currentState] != null && !shopActive && !invActive) { gameStates[currentState].keyPressed(k); }
		if(shopActive) shop.keyPressed(k);
		if(invActive) inv.keyPressed(k);
	}
	public void keyReleased(int k) {
		if(paused) { pauseState.keyReleased(k); }
		if(gameStates[currentState] != null && !shopActive && !invActive) gameStates[currentState].keyReleased(k);
		if(shopActive) shop.keyReleased(k);
		if(invActive) inv.keyReleased(k);
	}
	
}
