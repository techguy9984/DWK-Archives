package com.cpjd.stayfrosty.levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.stayfrosty.entity.Enemy;
import com.cpjd.stayfrosty.entity.Explosion;
import com.cpjd.stayfrosty.entity.HUD;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.menu.PauseState;
import com.cpjd.stayfrosty.tilemap.Background;
import com.cpjd.stayfrosty.tilemap.TileMap;

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
	
	// Plays an explosion when an enemy dies
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	//private MovingBlock mb;
	
	public Example_Level(GameStateManager gsm) {
		super(gsm);
		
		init();
	}

	public void init() {
		tileMap = new TileMap(64); // The tile size (in pixels) of each tile
		tileMap.load64("/Tilesets/tileset.png"); // The tileset artwork
		tileMap.loadTiledMap("/Maps/tutorial.txt"); // The map file, generated from Tiled
		tileMap.setPosition(300,1150); // The camera view
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/wood.png",0.1); // The background image location and it's movespeed

		player = new Player(tileMap, gsm);
		
		//mb = new MovingBlock(tileMap, 400, 1124, 200); // The x position, y position, and range
		
		player.setPosition(300, 1150); // Player location on tilemap
		
		populateEnemies(); // Adds all the enemies
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player); // The heads up display
		
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
		
		/*Illuminati il;
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
		spoders.add(spoder);
		
		Twelve twelve = new Twelve(tileMap);
		twelve.setPosition(1900, 850);
		//twelves.add(twelve);
		
		Bird nut = new Bird(tileMap, 720);
		nut.setPosition(720, 450);
		nuts.add(nut);
		
		Penguin p = new Penguin(tileMap, 507);
		p.setPosition(428, 1185);
		penguins.add(p);*/
	}
	
	public void update() {
		player.update();
		
		hud.update();		
		
		checkMelee();
		
		// Set the location of the background
		bg.setPosition(tileMap.getx(),tileMap.gety());
		
		// If we aren't freecaming, move the camera to the player location
		if(!Player.freecam) tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); 
		
		// Check for moving block collision
		//mb.checkPlayerCollision(player);

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
		
		if(k == KeyEvent.VK_W && GamePanel.DEBUG) {
			if(Player.freecam)tileMap.setPosition(tileMap.getx(), tileMap.gety() + 450);
			Player.freecam = true;
			
		}
		if(k == KeyEvent.VK_A && GamePanel.DEBUG) {
			if(Player.freecam)tileMap.setPosition(tileMap.getx() + 450, tileMap.gety());
			Player.freecam = true;
			
		}
		if(k == KeyEvent.VK_S && GamePanel.DEBUG) {
			if(Player.freecam)tileMap.setPosition(tileMap.getx(), tileMap.gety() - 450);
			Player.freecam = true;
		}
		if(k == KeyEvent.VK_D && GamePanel.DEBUG) {
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
