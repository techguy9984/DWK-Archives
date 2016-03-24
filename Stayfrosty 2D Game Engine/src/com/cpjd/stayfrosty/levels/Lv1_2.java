package com.cpjd.stayfrosty.levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Explosion;
import com.cpjd.stayfrosty.entity.HUD;
import com.cpjd.stayfrosty.entity.Minimap;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.entity.enemies.Troller;
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

// APPROVED
public class Lv1_2 extends GameState {
	
	private TileMap tileMap; 
	private Background bg;
	private Player player;

	private HUD hud;

	private ArrayList<Enemy> trollers;
	private ArrayList<Explosion> explosions;
	
	Minimap m;
	
	public Lv1_2(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		AudioPlayer.loopMusic(SKeys.Main);
		
		
		if(gsm.getState() != GameStateManager.entry) save(); 
		
		init();
	}

	public void init() {
		tileMap = new TileMap(64); // The tile size (in pixels) of each tile
		tileMap.load64("/Tilesets/tileset.png"); // The tileset artwork
		tileMap.loadTiledMap("/Maps/Lv1_2.txt"); // The map file, generated from Tiled
		tileMap.setPosition(300,1150); // The camera view
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/wood.png",0.1); // The background image location and it's movespeed

		player = new Player(tileMap, gsm, load);
		
		player.setPosition(160, 1670); // Player location on tilemap
		
		m = new Minimap("/Minimaps/Lv1_2.png",1 ,1);
		
		populateEnemies(); // Adds all the enemies
		
		hud = new HUD(player); // The heads up display

		if(gsm.getState() == GameStateManager.entry) save();
		
		explosions = new ArrayList<Explosion>();
		
	}

	private void save() {
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
	
	private void populateEnemies() {
		trollers = new ArrayList<Enemy>();

		Troller troll;
		troll = new Troller(tileMap);
		troll.setPosition(1640, 1680);
		trollers.add(troll);
		
		Troller troll2;
		troll2 = new Troller(tileMap);
		troll2.setPosition(1640, 700);
		trollers.add(troll2);
		
	}
	
	public void update() {
		player.update();
		
		hud.update();		
		
		checkMelee();
		
		// Set the location of the background
		bg.setPosition(tileMap.getx(),tileMap.gety());
		
		// If we aren't freecaming, move the camera to the player location
		if(!Player.freecam) tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); 
		
		// If we are debugging, set the location of the player to a mouse click
		if(GamePanel.debug && Mouse.isPressed(Mouse.LEFT)) {
			player.setPosition((int)Math.abs(tileMap.getx()) + (Mouse.x / 2), (int)Math.abs(tileMap.gety()) + (Mouse.y / 2));
		}
		
		checkBullets();
		
		removeEnemies();
		
		checkAttack();
		
		// Update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
			}
		}
		

	}
	
	private void checkBullets() {}
	
	private void checkAttack() {
		player.checkAttack(trollers);
	}
	
	private void checkMelee() {
		for(int i = 0; i < trollers.size(); i++) {
			if(trollers.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.hit(trollers.get(i).getDamage());
			}
		}
	}

	private void removeEnemies() {
		for (int i = 0; i < trollers.size(); i++) {
			Enemy e = trollers.get(i);
			e.update();
			if (e.isDead()) {
				trollers.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety())); 
			}
		}
	}
	
	public void draw(Graphics2D g) {

		// draw background
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		drawEnemies(g);
		
		// draw hud
		hud.draw(g);
		
		m.draw(g);
		
	}
	
	private void drawEnemies(Graphics2D g) {
		for (int i = 0; i < trollers.size(); i++) {
			trollers.get(i).draw(g);
		}
		// draw explosion
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(),(int) tileMap.gety());
			explosions.get(i).draw(g);
		}
	}
	
	public void keyPressed(int k) {
		player.keyPressed(k);
		
		
		m.keyPressed(k);		
		if(k == KeyEvent.VK_W && GamePanel.debug) {
			if(Player.freecam)tileMap.setPosition(tileMap.getx(), tileMap.gety() + 450);
			Player.freecam = true;
			
		}
		if(k == KeyEvent.VK_A && GamePanel.debug) {
			if(Player.freecam)tileMap.setPosition(tileMap.getx() + 450, tileMap.gety());
			Player.freecam = true;
			
		}
		if(k == KeyEvent.VK_S && GamePanel.debug) {
			if(Player.freecam)tileMap.setPosition(tileMap.getx(), tileMap.gety() - 450);
			Player.freecam = true;
		}
		if(k == KeyEvent.VK_D && GamePanel.debug) {
			if(Player.freecam) tileMap.setPosition(tileMap.getx() - 450, tileMap.gety());
			Player.freecam = true;
		};
		
		if(k == KeyEvent.VK_SPACE) {
			player.setUp(true);
			Player.freecam = false;
		}
		if(k == KeyEvent.VK_LEFT) {
			player.setLeft(true);
			Player.freecam = false;
		}
		if(k == KeyEvent.VK_DOWN) {
			player.setDown(true);
			Player.freecam = false;
		}
		if(k == KeyEvent.VK_RIGHT) {
			player.setRight(true);
			Player.freecam = false;
		}
		if(k == KeyEvent.VK_SPACE) {
			player.setJumping(true);
			Player.freecam = false;
		}
		
		if(k == KeyEvent.VK_SHIFT) player.setSprinting(true);
		if(k == KeyEvent.VK_S) player.setSmoking();
		if(k == KeyEvent.VK_ESCAPE && !PauseState.keyLock) gsm.setPaused(true);
			
	}
	
	public void keyReleased(int k) {
		player.keyReleased(k);

		PauseState.keyLock = false;
		
		if(k == KeyEvent.VK_SPACE) player.setUp(false);
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_SPACE) player.setJumping(false);
		if(k == KeyEvent.VK_SHIFT) player.setSprinting(false);
		
	}
	
}