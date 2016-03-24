package com.cpjd.stayfrosty.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Center;

public class Shop extends GameState {
	
	// The current state
	public static int previousState; 
	
	//private int currentPage;
	//private int totalPages;
	
	// Images
	BufferedImage pistol;
	BufferedImage ak47;
	BufferedImage shotgun;
	BufferedImage m4a1;
	BufferedImage sniper;
	BufferedImage uzi;
	private double compensate;
	
	// Memes
	BufferedImage memes;
	
	// Selection
	BufferedImage select;
	private int sx;
	private int sy;
	private int currentSelection;

	// Purchased
	BufferedImage[] check = new BufferedImage[6];
	public static int[] purchases = new int[6];
	
	// Transition
	private int ystart;
	private int ystart2;
	
	// States
	private int currentState;
	
	// Upgrades
	Upgrades upgrades;
	Consumables consumables;
	
	private int state;
	
	public Shop(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		resetTransition();
		
		currentSelection = 1;

		sx = Center.align(7) + (int)compensate;
		sy = Center.aligny(ystart);
		
		state = gsm.getState();
		
		upgrades = new Upgrades(load);
		consumables = new Consumables(gsm, load);
		
		currentState = 1;

		init();
		
		try {
			pistol = ImageIO.read(getClass().getResourceAsStream("/Shop/pistol.png"));
			ak47 = ImageIO.read(getClass().getResourceAsStream("/Shop/ak-47.png"));
			shotgun = ImageIO.read(getClass().getResourceAsStream("/Shop/shotgun.png"));
			m4a1 = ImageIO.read(getClass().getResourceAsStream("/Shop/m4a1.png"));
			sniper = ImageIO.read(getClass().getResourceAsStream("/Shop/sniper.png"));
			uzi = ImageIO.read(getClass().getResourceAsStream("/Shop/uzi.png"));
			select = ImageIO.read(getClass().getResourceAsStream("/Shop/select.png"));
			memes = ImageIO.read(getClass().getResourceAsStream("/Shop/memes.png"));
			
			
			for(int i = 0; i < 6; i++) {
				check[i] = ImageIO.read(getClass().getResourceAsStream("/Shop/check.png"));
			}	
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public void init() {

		purchases = load.getPurchases();
	}
	
	// Dank memes is at [6][0]	
	public void update() {
		
		if(state != gsm.getState()) {
			state = gsm.getState();
			init();
		}
		
		if(currentState == 1) {
			if(ystart > 20) ystart -= 15;
			if(ystart2 > 60) ystart2 -= 15;
			if(ystart < 20) ystart = 20;
			if(ystart2 < 60) ystart2 = 60;
			
			// Check to make sure shop is centered on all screen sizes
			int tempLeft = Center.align(7) + (int)compensate;
			int tempRight = (GamePanel.WIDTH - (Center.align(67) + shotgun.getWidth()  + (int)compensate));
			if(tempLeft < tempRight) compensate++;
			if(tempLeft > tempRight) compensate--;
			
			manageSelect();
			
			managePrice();
		}
		if(currentState == 2) { // Upgrades
			upgrades.update();
			
		}
		if(currentState == 3) {
			consumables.update();
		}
		
	}
	
	private void managePrice() {
		if(currentSelection == 1) {
			if(Player.currentMemes >= 1) switchImage("select");
			else switchImage("unselect");
		}
		if(currentSelection == 2) {
			if(Player.currentMemes >= 10) switchImage("select");
			else switchImage("unselect");
		}
		if(currentSelection == 3) {
			if(Player.currentMemes >= 20) switchImage("select");
			else switchImage("unselect");
		}
		if(currentSelection == 4) {
			if(Player.currentMemes >= 30) switchImage("select");
			else switchImage("unselect");
		}
		if(currentSelection == 5) {
			if(Player.currentMemes >= 35) switchImage("select");
			else switchImage("unselect");
		}
		if(currentSelection == 6) {
			if(Player.currentMemes >= 40) switchImage("select");
			else switchImage("unselect");
		}
	}
	
	private void manageSelect() {
		if(currentSelection == 1) {
			sx = Center.align(7) + (int)compensate;
			sy = Center.aligny(ystart);
		}
		if(currentSelection == 2) {
			sx = Center.align(37) + (int)compensate;
			sy = Center.aligny(ystart);
		}
		if(currentSelection == 3) {
			sx = Center.align(67) + (int)compensate;
			sy = Center.aligny(ystart);
		}
		if(currentSelection == 4) {
			sx = Center.align(7) + (int)compensate;
			sy = Center.aligny(ystart2);
		}
		if(currentSelection == 5) {
			sx = Center.align(37) + (int)compensate;
			sy = Center.aligny(ystart2);
		}
		if(currentSelection == 6) {
			sx = Center.align(67) + (int)compensate;
			sy = Center.aligny(ystart2);
		}
	}

	public void draw(Graphics2D g) {
		// Clear screen
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
		
		// Draw title
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,25));
		g.drawString("Shop", 5, 25);
		
		// Draw help
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.PLAIN,10));
		g.drawString("Q | E & WASD to navigate shop",Center.center(g, "Q | E & WASD to navigate shop"), 50);
		
		// Draw tabs
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,18));
		g.drawString("|", Center.align(40), 28);
		g.drawString("|", Center.center(g, "Upgrades") + Center.align(13.3), 28);
		if(currentState == 2) g.setColor(Color.RED);
		g.drawString("Upgrades", Center.center(g, "Upgrades"), 30);
		g.setColor(Color.BLACK);
		if(currentState == 3) g.setColor(Color.RED);
		g.drawString("Consumables", Center.align(62), 30);
		g.setColor(Color.BLACK);
		if(currentState == 1) g.setColor(Color.RED);
		g.drawString("Weapons", Center.align(26), 30);
		
		// Draw memes
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf((int)Player.currentMemes), GamePanel.WIDTH - memes.getWidth() - 20 - Center.getSWidth(g, String.valueOf((int)Player.currentMemes)), 38);
		g.drawImage(memes, GamePanel.WIDTH - memes.getWidth() - 10, 10, null);
		
		// States
		if(currentState == 1) drawShop(g);
		if(currentState == 2) upgrades.draw(g);
		if(currentState == 3) consumables.draw(g);
		

	}

	private void drawShop(Graphics2D g) {
		// Draw weapons
		g.drawImage(pistol,Center.align(7) + (int)compensate,Center.aligny(ystart),null);
		g.drawImage(uzi,Center.align(37) + (int)compensate,Center.aligny(ystart),null);
		g.drawImage(shotgun, Center.align(67)+(int)compensate, Center.aligny(ystart), null);
		g.drawImage(ak47,Center.align(7) + (int)compensate,Center.aligny(ystart2),null);
		g.drawImage(m4a1,Center.align(37) + (int)compensate,Center.aligny(ystart2),null);
		g.drawImage(sniper, Center.align(67) + (int)compensate, Center.aligny(ystart2), null);

		// Draw information message
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,10));
		g.drawString("Guns must be purchased in order.", 5, GamePanel.HEIGHT - 12);
		
		// Draw selection
		g.drawImage(select, sx - 5, sy - 5, null);
		
		// Draw checks over purchased items
		if(purchases[0] == 1) g.drawImage(check[0], Center.align(7) + (int)compensate, Center.aligny(ystart), null);
		if(purchases[1] == 1) g.drawImage(check[1], Center.align(37) + (int)compensate, Center.aligny(ystart), null);
		if(purchases[2] == 1) g.drawImage(check[2], Center.align(67) + (int)compensate, Center.aligny(ystart), null);
		if(purchases[3] == 1) g.drawImage(check[3], Center.align(7) + (int)compensate, Center.aligny(ystart2), null);
		if(purchases[4] == 1) g.drawImage(check[4], Center.align(37) + (int)compensate, Center.aligny(ystart2), null);
		if(purchases[5] == 1) g.drawImage(check[5], Center.align(67) + (int)compensate, Center.aligny(ystart2), null);
	}
	
	private void switchImage(String name) {
		try {
			select = ImageIO.read(getClass().getResourceAsStream("/Shop/"+name+".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	public void resetTransition() {
		ystart = GamePanel.HEIGHT;
		ystart2 = GamePanel.HEIGHT + 200;
	}
	
	private void calcTransaction() {
		
		
		if(currentSelection == 1 && Player.currentMemes >= 1 && purchases[0] == 0) {
			Player.currentMemes -= 1;
			purchases[0] = 1;
			AudioPlayer.playSound(SKeys.Buy);
			init();
		} else if(currentSelection == 1 && Player.currentMemes < 1){
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 2 && Player.currentMemes >= 10 && purchases[1] == 0 && purchases[0] == 1) {;
			Player.currentMemes -= 10;
			purchases[1] = 1;
			AudioPlayer.playSound(SKeys.Buy);
			init();
		} else if(currentSelection == 2 && Player.currentMemes < 10) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if (currentSelection == 3 && Player.currentMemes >= 20 && purchases[2] == 0 && purchases[0] == 1
				&& purchases[1] == 1) {
			Player.currentMemes -= 20;
			purchases[2] = 1;
			AudioPlayer.playSound(SKeys.Buy);
			init();
		} else if(currentSelection == 3 && Player.currentMemes < 20) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 4 && Player.currentMemes >= 30 && purchases[3] == 0 && purchases[2] == 1 && purchases[1] == 1 &&
				purchases[0] == 1) {
			Player.currentMemes -= 30;
			purchases[3] = 1;
			AudioPlayer.playSound(SKeys.Buy);
			init();
		} else if(currentSelection == 4 && Player.currentMemes < 30) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 5 && Player.currentMemes >= 35 && purchases[4] == 0 && purchases[3] == 1 && purchases[2] == 1 &&
				purchases[1] == 1 && purchases[0] == 1) {
			Player.currentMemes -= 35;
			purchases[4] = 1;
			AudioPlayer.playSound(SKeys.Buy);
			init();
		} else if(currentSelection == 5 && Player.currentMemes < 35) {
			AudioPlayer.playSound(SKeys.Error);
		}
		if(currentSelection == 6 && Player.currentMemes >= 40 && purchases[5] == 0 && purchases[4] == 1 && purchases[3] == 1 &&
				purchases[2] == 1 && purchases[1] == 1 && purchases[0] == 1) {
			Player.currentMemes -= 40;
			purchases[5] = 1;
			init();
			AudioPlayer.playSound(SKeys.Buy);
		} else if(currentSelection == 6 && Player.currentMemes < 40) {
			AudioPlayer.playSound(SKeys.Error);
		}
	}

	private void manageKeys(int k) {
		if(k == KeyEvent.VK_LEFT) {
			if(currentSelection > 1) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection--;
			}
		}
		if(k == KeyEvent.VK_RIGHT) {
			if(currentSelection < 6) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection++;
			}
		}
		if(k == KeyEvent.VK_UP) {
			if(currentSelection > 3) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection -= 3;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			if(currentSelection <= 3) {
				AudioPlayer.playSound(SKeys.Change);
				currentSelection += 3;
			}
		}
		if(k == KeyEvent.VK_ENTER) {
			calcTransaction();
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_E) {
			AudioPlayer.playSound(SKeys.Change);
			currentState++;
			if(currentState > 3) currentState = 3;
		}
		if(k == KeyEvent.VK_Q) {
			AudioPlayer.playSound(SKeys.Change);
			currentState--;
			if(currentState < 1) currentState = 1;
		}
		if(k == KeyEvent.VK_O) {
			gsm.setState(previousState);
		}
		
		if(currentState == 1) manageKeys(k);
		if(currentState == 3) {
			consumables.keyPressed(k);
		}
		if(currentState == 2) {
			upgrades.keyPressed(k);
		}
		
	}

	
	public void keyReleased(int k) {
		consumables.keyReleased(k);
		upgrades.keyReleased(k);
	}
	
}
