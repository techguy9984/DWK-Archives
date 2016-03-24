package com.cpjd.stayfrosty.levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Explosion;
import com.cpjd.stayfrosty.entity.HUD;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.entity.enemies.Illuminati;
import com.cpjd.stayfrosty.entity.enemies.Bird;
import com.cpjd.stayfrosty.entity.enemies.Penguin;
import com.cpjd.stayfrosty.entity.enemies.Troller;
import com.cpjd.stayfrosty.entity.enemies.Twelve;
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
import com.cpjd.stayfrosty.weapons.MultiBullet;
import com.cpjd.stayfrosty.weapons.Weapon;

public class Example_Level extends GameState {
	
	/* This is the example level. Every level needs a class like this one. First off, minimize this methods:
	 * 1) keyPressed(int k) & keyReleased(int k)
	 * 2) update()
	 * 3) The constructor
	 * 3) All other methods except init()
	 * Configure these things
	 * 1) In the init() method, use tileMap.loadTiledMap(String) to load in your Tiled map file
	 * 2) Launch the game and use F6 to navigate to the spawn coordinates. Write these down and set them in tileMap.setPosition() & player.setPosition()
	 * 3) Use JukeBox.load(String path, String name) and JukeBox.loop(String name) to play music
	 * 4) Use player.removeCrates() to remove all crates if a certain circumstance occurs
	 * To add an enemy
	 * 1) At the top, at private ArrayList<Enemy> <name>
	 * 2) In populate enemies, use: <type> = new ArrayList<Enemy>();
	 * 3) Create a new ememy with <Type> <tempName> = new <Type>)();
	 * 4) Call enemy.setPosition() for the correct coordinates
	 * 5) Add them to the array
	 * 6) Add an array check from checkBullets(), checkMelee(), checkAttack(), removeEnemies() & drawEnemies
	 * You basically just copy the code you need from this class, it contains everything you need.
	 * 
	 * Important level notes -
	 * -Levels can be restarted at any time
	 * -Game saves are at the beginning of a level, if you exit in the middle, you have to restart at the beginning
	 * -Ladders must be one tile higher than the ground nearby them
	 * -Moving block Y position MUST be a multiple of 64
	 * -Good luck
	 * 
	 * Tools - 
	 * Pressing F6 effects
	 * - WASD to look around the map
	 * - Click to teleport player
	 * - Player doesn't lose endurance or health
	 * - Player continously receives 50 dank memes (doesn't receive them when viewing shop or inventory)
	 * - Press L to load a specific level
	 * - Displays all entity hitboxes, as well as lines pointing to all enemy locations
	 * - Coordinates of the current mouse position are shown in the lower left corner
	 * 
	 */
	
	private TileMap tileMap; 
	private Background bg;
	private Player player;
	
	// Enemys
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> illuminati;
	private ArrayList<Enemy> trollers;
	private ArrayList<Enemy> doggs;
	private ArrayList<Enemy> spoders;
	private ArrayList<Enemy> twelves;
	private ArrayList<Enemy> nuts;
	private ArrayList<Enemy> penguins;
	
	// A temporary arraylist for checking player & enemy bullet collisions
	ArrayList<MultiBullet> tempBullets;
	
	// Plays an explosion when an enemy dies
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	//private MovingBlock mb;
	
	public Example_Level(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		if(gsm.getState() != GameStateManager.entry) save(); 
		
		init();
	}

	public void init() {
		tileMap = new TileMap(64); // The tile size (in pixels) of each tile
		tileMap.load64("/Tilesets/tileset.png"); // The tileset artwork
		tileMap.loadTiledMap("/Maps/tutorial.txt"); // The map file, generated from Tiled
		tileMap.setPosition(300,1150); // The camera view
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/wood.png",0.1); // The background image location and it's movespeed

		player = new Player(tileMap, gsm, load);
		
		//mb = new MovingBlock(tileMap, 400, 1124, 200); // The x position, y position, and range
		
		player.setPosition(300, 1150); // Player location on tilemap
		
		populateEnemies(); // Adds all the enemies
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player); // The heads up display

		if(gsm.getState() == GameStateManager.entry) save();
		
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
		// Create all of the enemy arrays
		enemies = new ArrayList<Enemy>();
		illuminati = new ArrayList<Enemy>();
		trollers = new ArrayList<Enemy>();
		doggs = new ArrayList<Enemy>();
		spoders = new ArrayList<Enemy>();
		twelves = new ArrayList<Enemy>();
		nuts = new ArrayList<Enemy>();
		penguins = new ArrayList<Enemy>();
		
		/*Slugger s;
		Point[] points = {
				new Point(200, 100), new Point(860, 200), 
				new Point(1525, 200), new Point(1680, 200),
				new Point(1800,200)
		};
		for(int i = 0; i < points.length; i++) {	
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}*/
		
		Illuminati il;
		il = new Illuminati(tileMap);
		il.setPosition(1900, 850);
		illuminati.add(il);
		
		Troller troll;
		troll = new Troller(tileMap);
		troll.setPosition(1400, 800);
		trollers.add(troll);
		
		/*Dogg dogg;
		dogg = new Dogg(tileMap);
		dogg.setPosition(100,1100);
		dogg.setStartX(300);
		doggs.add(dogg);*/
		
		/*Spoder spoder;
		spoder = new Spoder(tileMap);
		spoder.setPosition(1900, 850);
		spoders.add(spoder);*/
		
		Twelve twelve = new Twelve(tileMap);
		twelve.setPosition(1900, 850);
		//twelves.add(twelve);
		
		Bird nut = new Bird(tileMap, 720);
		nut.setPosition(720, 450);
		nuts.add(nut);
		
		Penguin p = new Penguin(tileMap, 507);
		p.setPosition(428, 1185);
		penguins.add(p);
	}
	
	public void update() {
		player.update();
		
		hud.update();		
		
		checkMelee();
		
		// Set the location of the background
		bg.setPosition(tileMap.getx(),tileMap.gety());
		
		// For the first level, we remove the crates when the player buys the pistol instead of picking up a key
		if(load.getPurchases()[0] == 1) player.removeCrates();
		
		// If we aren't freecaming, move the camera to the player location
		if(!Player.freecam) tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); 
		
		// If we are debugging, set the location of the player to a mouse click
		if(GamePanel.debug && Mouse.isPressed(Mouse.LEFT)) {
			player.setPosition((int)Math.abs(tileMap.getx()) + (Mouse.x / 2), (int)Math.abs(tileMap.gety()) + (Mouse.y / 2));
		}
		
		// Check for moving block collision
		//mb.checkPlayerCollision(player);
		 
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
	
	private void checkBullets() {
		// Enemy attacks
		// Test for bullet collisions on illuminati
		for(int i = 0; i < illuminati.size(); i++) {
			// Run through all of the bullets (illuminati)
			for(int j = 0; j < illuminati.get(i).getBulletArray().size(); j++) {
				if(illuminati.get(i).getBulletArray().get(j).getCollisionBox().intersects(player.getCollisionBox())) {
					if(!illuminati.get(i).isDead()) player.hit(illuminati.get(i).getDamage());
				}
			}
		}
		
		// Deez Nuts
		for(int i = 0; i < nuts.size(); i++) {
			for(int j = 0; j < nuts.get(i).getBulletArray().size(); j++) {
				if(nuts.get(i).getBulletArray().get(j).getCollisionBox().intersects(player.getCollisionBox())) {
					if(!nuts.get(i).isDead()) player.hit(nuts.get(i).getDamage());
				}
			}
		}

		// Bullet collisions for doggs
		for (int i = 0; i < doggs.size(); i++) {
			// Run through all of the bullets (doggs)
			for (int j = 0; j < doggs.get(i).getBulletArray().size(); j++) {
				if (doggs.get(i).getBulletArray().get(j).getCollisionBox().intersects(player.getCollisionBox())) {
					if (!doggs.get(i).isDead()) {
						doggs.get(i).runProbs();
						player.hit(doggs.get(i).getStolenHealth());
						player.removeDoritoes(doggs.get(i).getStolenDoritoes());
						player.removeMemes(doggs.get(i).getStolenMemes());
					}
				}
			}
		}
		// Penguins
		for(int i = 0; i < penguins.size(); i++) {
			for(int j = 0; j < penguins.get(i).getBulletArray().size(); j++) {
				if(penguins.get(i).getBulletArray().get(j).getCollisionBox().intersects(player.getCollisionBox())) {
					if(!penguins.get(i).isDead()) player.hit(penguins.get(i).getDamage());
				}
			}
		}
		
	}
	
	private void checkAttack() {
		// Checks the player's bullets to see if they hit an enemy
		player.checkAttack(enemies);
		player.checkAttack(illuminati);
		player.checkAttack(trollers);
		player.checkAttack(doggs);
		player.checkAttack(spoders);
		for(int i = 0; i < spoders.size(); i++) {
			player.checkAttack(spoders.get(i).getBabies());
		}
		player.checkAttack(twelves);
		player.checkAttack(nuts);
		player.checkAttack(penguins);
 		
	}
	
	private void checkMelee() {
		// Checks to see if the enemy hit the player
		for(int i = 0; i < trollers.size(); i++) {
			if(trollers.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.hit(trollers.get(i).getDamage());
			}
		}
		
		for(int i = 0; i < doggs.size(); i++) {
			if(doggs.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.removeAllPowerups();
			}
		}
		
		for(int i = 0; i < spoders.size(); i++) {
			if(spoders.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.hit(spoders.get(i).getDamage());
			}
		}
		for(int i = 0; i < twelves.size(); i++) {
			if(twelves.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.hit(twelves.get(i).getDamage());
			}
		}
		
		for(int i = 0; i < spoders.size(); i++) {
			for(int j = 0; j < spoders.get(i).getBabies().size(); j++) {
				if(spoders.get(i).getBabies().get(j).getCollisionBox().intersects(player.getCollisionBox())) {
					player.hit(spoders.get(i).getBabies().get(j).getDamage());
				}
			}
		}
		
		for(int i = 0; i < nuts.size(); i++) {
			if(nuts.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.hit(nuts.get(i).getDamage());
			}
		}
		for(int i = 0; i < penguins.size(); i++) {
			if(penguins.get(i).getCollisionBox().intersects(player.getCollisionBox())) {
				player.hit(penguins.get(i).getDamage());
			}
		}
		
	}
	
	private void removeEnemies() {
		// Removes / updates enemies
		// Illuminati
		for (int i = 0; i < illuminati.size(); i++) {
			Enemy e = illuminati.get(i);
			e.update();
			if (e.isDead()) {
				illuminati.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety())); 
			}
		}
		// Trollers
		for (int i = 0; i < trollers.size(); i++) {
			Enemy e = trollers.get(i);
			e.update();
			if (e.isDead()) {
				trollers.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety())); 
			}
		}
		
		// Doggs
		for (int i = 0; i < doggs.size(); i++) {
			Enemy e = doggs.get(i);
			e.update();
			if(e.isDead()) {
				// This actually add instead of subtract
				player.removeDoritoes(-doggs.get(i).getTotalDoritoes());
				player.removeMemes(-doggs.get(i).getTotalMemes());
				doggs.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		// Spoders
		for (int i = 0; i < spoders.size(); i++) {
			Enemy e = spoders.get(i);
			e.update();
			if(e.isDead()) {
				spoders.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		// Baby spoders
		for (int i = 0; i < spoders.size(); i++) {
			for(int j = 0; j < spoders.get(i).getBabies().size(); j++) {
				spoders.get(i).getBabies().get(j).update();
				if(spoders.get(i).getBabies().get(j).isDead()) {
					spoders.get(i).getBabies().remove(j);
					j--;	
				}
			}
		}
		// Twelves
		for (int i = 0; i < twelves.size(); i++) {
			Enemy e = twelves.get(i);
			e.update();
			if(e.isDead()) {
				twelves.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		// Nuts
		for (int i = 0; i < nuts.size(); i++) {
			Enemy e = nuts.get(i);
			e.update();
			if(e.isDead()) {
				nuts.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		// Penguins
		for(int i = 0; i < penguins.size(); i++) {
			Enemy e = penguins.get(i);
			e.update();
			if(e.isDead()) {
				penguins.remove(i);
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
		
		// moving block
		//mb.draw(g);
		

		drawEnemies(g);
		
		// draw explosion
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(),(int) tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
		// draw player
		player.draw(g);
		
		
	}
	
	private void drawEnemies(Graphics2D g) {
		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		for (int i = 0; i < illuminati.size(); i++) {
			illuminati.get(i).draw(g);
		}
		for (int i = 0; i < trollers.size(); i++) {
			trollers.get(i).draw(g);
		}
		for (int i = 0; i < doggs.size(); i++) {
			doggs.get(i).draw(g);
		}
		for (int i = 0; i < spoders.size(); i++) {
			spoders.get(i).draw(g);
			for(int j = 0; j < spoders.get(i).getBabies().size(); j++) {
				spoders.get(i).getBabies().get(j).draw(g);
			}
		}
		for(int i = 0; i < twelves.size(); i++) {
			twelves.get(i).draw(g);
		}
		for(int i = 0; i < nuts.size(); i++) {
			nuts.get(i).draw(g);
		}
		for(int i = 0; i < penguins.size(); i++) {
			penguins.get(i).draw(g);
		}
	}
	
	public void keyPressed(int k) {
		player.keyPressed(k);
		
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
		
		if(k == KeyEvent.VK_SPACE) player.setUp(true);
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		
		
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
