package com.cpjd.stayfrosty.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Explosion;
import com.cpjd.stayfrosty.entity.HUD;
import com.cpjd.stayfrosty.entity.Minimap;
import com.cpjd.stayfrosty.entity.MovingBlock;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.entity.enemies.Illuminati;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.input.Mouse;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.menu.PauseState;
import com.cpjd.stayfrosty.shop.Consumables;
import com.cpjd.stayfrosty.shop.Shop;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.Background;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.Weapon;

// APROVED
public class Lv1_1 extends GameState {// intro
	
	//private TileMap tileMap; 
	//private Background bg;
	private int counter = 0;
	private int storyLevel = 0;
	private String[] Narration = new String[10];
	
	public Lv1_1(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		if(gsm.getState() != GameStateManager.entry) save();
		
		init();
	}
	

	public void init() {
		storyLevel = 0;
		Narration[0] = " The DWK Archives: vol 1";
		Narration[1] = " Sometime in the ancient past three great warriors formed a bond, this bond was more powerful than any other known to man.";
		Narration[2] = " They drew upon each other's strengths to outwit, outlast, and overpower their enemies.";
		Narration[3] = " These three great warriors eventually died together in battle. However, their legacy was continued by their followers. Their following formed a union with one purpose, to eradicate the world of injustice.";
		Narration[4] = " This purpose was upheld by the great constitution which held the rules for the perfect society. The followers of this constitution began to be called the illuminati.";
		Narration[5] = " Those under the illuminati experienced great peace and wealth and the constitution was upheld at all costs.";
		Narration[6] = " One day, a young man by the name of Alex Sparker infiltrated the illuminati and attempted to corrupt its followers.";
		Narration[7] = " His plan succeeded, many fell to his sly words and the illuminati began to fall. The days of peace and prosperity seemed to come to an end, the illuminati lost its fame and became only legend.";
		Narration[8] = " Until one day, when three new young heroes joined together to fight the evil plans of Alex Sparker and his new found followers, and to promote justice found in the ideals of the illuminati.";
		Narration[9] = " Here is the tale of the victory of the three united, the DWK.";

		
		AudioPlayer.loopMusic(SKeys.Main);

		if(gsm.getState() == GameStateManager.entry) save();
		
		
	}
	protected void save() {
		// Run some counting stuff
		int pistolUpgrades = 0;
		int uziUpgrades = 0;
		int shotgunUpgrades = 0;
		int akUpgrades = 0;
		int m4Upgrades = 0;
		int sniperUpgrades = 0;
		int enduranceUpgrades = 0;
		int healthUpgrades = 0;
		int powerupUpgrades = 0;
		int armorUpgrades = 0;
		int mixtapeUpgrades = 0;

		for(int i = 0; i < 5; i++) {
			if(Upgrades.bought[0][i] == true) pistolUpgrades++;
			if(Upgrades.bought[1][i] == true) uziUpgrades++;
			if(Upgrades.bought[2][i] == true) shotgunUpgrades++;
			if(Upgrades.bought[3][i] == true) akUpgrades++;
			if(Upgrades.bought[4][i] == true) m4Upgrades++;
			if(Upgrades.bought[5][i] == true) sniperUpgrades++;
			if(Upgrades.bought[7][i] == true) enduranceUpgrades++;
			if(Upgrades.bought[8][i] == true) healthUpgrades++;
			if(Upgrades.bought[9][i] == true) powerupUpgrades++;
			if(Upgrades.bought[10][i] == true) armorUpgrades++;
			if(Upgrades.bought[12][i] == true) mixtapeUpgrades++;
		}
		
		// Save command must be run here
		save.setValue(0, GamePanel.versionCode);
		save.setValue(1, gsm.getState());
		save.setValue(2, (int)Player.currentMemes);
		save.setValue(3, Weapon.clip[0]);
		save.setValue(4, Weapon.ammo[0]);
		save.setValue(5, Weapon.clip[1]);
		save.setValue(6, Weapon.ammo[1]);
		save.setValue(7, Weapon.clip[2]);
		save.setValue(8, Weapon.ammo[2]);
		save.setValue(9, Weapon.clip[3]);
		save.setValue(10, Weapon.ammo[3]);
		save.setValue(11, Weapon.clip[4]);
		save.setValue(12, Weapon.ammo[4]);
		save.setValue(13, Weapon.clip[5]);
		save.setValue(14, Weapon.ammo[5]);
		save.setValue(15, Shop.purchases[0]);
		save.setValue(16, Shop.purchases[1]);
		save.setValue(17, Shop.purchases[2]);
		save.setValue(18, Shop.purchases[3]);
		save.setValue(19, Shop.purchases[4]);
		save.setValue(20, Shop.purchases[5]);
		save.setValue(21, pistolUpgrades);
		save.setValue(22, uziUpgrades);
		save.setValue(23, shotgunUpgrades);
		save.setValue(24, akUpgrades);
		save.setValue(25, m4Upgrades);
		save.setValue(26, sniperUpgrades);
		save.setValue(27, enduranceUpgrades);
		save.setValue(28, healthUpgrades);
		save.setValue(29, powerupUpgrades);
		save.setValue(30, armorUpgrades);
		save.setValue(31, mixtapeUpgrades);
		save.setValue(32, Consumables.healths.size());
		save.setValue(33, Consumables.powerups.size());
		save.setValue(34, Consumables.shields.size());
		save.setValue(35, Consumables.skips.size());
		save.setValue(36, 4);
		int temp1 = 0;
		int temp2 = 0;
		int temp3 = 0;
		int temp4 = 0;
		if(GamePanel.highQuality) temp1 = 1;
		if(GamePanel.joe) temp2 = 1;
		if(GamePanel.hideCursor) temp3 = 1;
		
		save.setValue(37, temp1);
		save.setValue(38, temp2);
		save.setValue(39, temp3);
		save.setValue(40, temp4);
		
		save.save();
		load.reload();
	}
	
	
	
	public void update() {
		counter ++;
		if(counter>Narration[storyLevel].length()*2){
			counter = Narration[storyLevel].length()*2;
		}
		if (storyLevel == 10){
        	//gsm.nextLevel();
        }
		

	}
	
	

	


	
	
	public void draw(Graphics2D g) {

		//bg.draw(g);
		g.setColor(Color.white);
		
		Font font = new Font("Monospaced", Font.PLAIN, 18);
        g.setFont( font );
		
        displayString(Narration[storyLevel].substring(0,counter/2), 20, 20, 50, 20, g);


		
	}
	
	public static void displayString(String str, int x, int y, int width,int height, Graphics g){
		while(str.length() > width){
			for(int i = width-1;i>=0;i--){
				if(str.charAt(i)==' '){
					g.drawString(str.substring(0,i), x, y);
					str = str.substring(i,str.length());
					y += height;
					break;
					
				}
			}
		}
		g.drawString(str,x,y);
	}
	
	public void keyPressed(int k) {
		//player.keyPressed(k);
		
		//m.keyPressed(k);
		
		
		
		//if(k == KeyEvent.VK_SPACE) player.setUp(true);
		//if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		//if(k == KeyEvent.VK_DOWN) player.setDown(true);
		//if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		//if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		
		
		//if(k == KeyEvent.VK_SHIFT) player.setSprinting(true);
		//if(k == KeyEvent.VK_S) player.setSmoking();
		if(k == KeyEvent.VK_ENTER){
			storyLevel++;
			counter = 0;
		}
		if(k == KeyEvent.VK_ESCAPE && !PauseState.keyLock) {
			gsm.setPaused(true);
		}
			
	}
	
	public void keyReleased(int k) {
		//player.keyReleased(k);

		PauseState.keyLock = false;
		
		//if(k == KeyEvent.VK_SPACE) player.setUp(false);
		//if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		//if(k == KeyEvent.VK_DOWN) player.setDown(false);
		//if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		//if(k == KeyEvent.VK_SPACE) player.setJumping(false);
		//if(k == KeyEvent.VK_SHIFT) player.setSprinting(false);
		
	}
	
	
}
