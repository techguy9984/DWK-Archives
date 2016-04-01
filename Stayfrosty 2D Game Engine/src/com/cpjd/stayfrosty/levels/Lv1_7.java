package com.cpjd.stayfrosty.levels;

import java.awt.Graphics2D;
import java.awt.Point;
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
import com.cpjd.stayfrosty.entity.enemies.Troller;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.input.Mouse2;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.menu.PauseState;
import com.cpjd.stayfrosty.shop.Consumables;
import com.cpjd.stayfrosty.shop.Shop;
import com.cpjd.stayfrosty.shop.Upgrades;
import com.cpjd.stayfrosty.tilemap.Background;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.stayfrosty.weapons.Weapon;

// APPROVED
public class Lv1_7 extends GameState {
	
	private TileMap tileMap; 
	private Background bg;
	private Player player;

	private HUD hud;
	
	private ArrayList<Explosion> explosions;
	
	private ArrayList<Enemy> illuminati;
	private ArrayList<Enemy> trollers;
	
	MovingBlock m1;
	MovingBlock m2;
	MovingBlock m3;
	MovingBlock m4;
	
	Minimap m;
	
	public Lv1_7(GameStateManager gsm, Load load, Save save) {
		super(gsm, load, save);
		
		if(gsm.getState() != GameStateManager.entry) save();
		
		init();
	}
	
	public void init() {
		tileMap = new TileMap(64);
		tileMap.load64("/Tilesets/tileset.png");
		tileMap.loadTiledMap("/Maps/Lv1_7.txt");
		tileMap.setPosition(0,0);
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/wood.png",0.1);

		player = new Player(tileMap, gsm, load);
		
		AudioPlayer.loopMusic(SKeys.Main);
		
		
		player.setPosition(1181, 1626);
		
		m1 = new MovingBlock(tileMap, 200, 670, 2243 - 200);
		m1.setMoveSpeed(3.2);
		
		m2 = new MovingBlock(tileMap, 300, 510, 2443 - 300);
		m2.setMoveSpeed(3.2);
		
		m3 = new MovingBlock(tileMap, 400, 355, 2443 - 400);
		m3.setMoveSpeed(3.2);
		
		m4 = new MovingBlock(tileMap, 1050, 818, 250);
		m4.setMoveSpeed(3.2);
		
		populateEnemies();
		
		hud = new HUD(player); 

		explosions = new ArrayList<Explosion>();

		m = new Minimap("/Minimaps/Lv1_7.png",1,1);
		
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
		illuminati = new ArrayList<Enemy>();
		trollers = new ArrayList<Enemy>();
		
		// 1780
		
		Troller t1 = new Troller(tileMap);
		t1.setPosition(533, 1160);
		trollers.add(t1);
		
		Troller t2 = new Troller(tileMap);
		t2.setPosition(1757, 1160);
		trollers.add(t2);
		
		Troller t3 = new Troller(tileMap);
		t3.setPosition(2128, 1160);
		trollers.add(t3);
		
		Point[] point = {
			new Point(200, 1780),new Point(400, 1780),new Point(600, 1780), new Point(800, 1780),
			new Point(1500, 1780),new Point(1700,1780), new Point(1900,1780), new Point(2100,1780)
		};
		
		for(int i = 0; i < point.length; i++) {
			Illuminati il = new Illuminati(tileMap);
			il.setPosition(point[i].x, point[i].y);
			illuminati.add(il);
		}

	}
	
	public void update() {
		player.update();
		
		hud.update();		
		
		checkMelee();
		
		bg.setPosition(tileMap.getx(),tileMap.gety());
		
		if(!Player.freecam) tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); 
		
		if(GamePanel.debug && Mouse2.isPressed(Mouse2.LEFT)) {
			player.setPosition((int)Math.abs(tileMap.getx()) + (Mouse2.x / 2), (int)Math.abs(tileMap.gety()) + (Mouse2.y / 2));
		}
		
		m1.update();
		m2.update();
		m3.update();
		m4.update();
		
		if(Player.globalY < 750 && Player.globalY > 550) m1.checkPlayerCollision(player);
		if(Player.globalY < 550 && Player.globalY > 350) m2.checkPlayerCollision(player);
		if(Player.globalY < 300) m3.checkPlayerCollision(player);
		if(Player.globalY > 750) m4.checkPlayerCollision(player);
		
		
		checkBullets();
		
		removeEnemies();
		
		checkAttack();
		
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
			}
		}
	}
	
	private void checkBullets() {
		for(int i = 0; i < illuminati.size(); i++) {
			for(int j = 0; j < illuminati.get(i).getBulletArray().size(); j++) {
				if(illuminati.get(i).getBulletArray().get(j).getCollisionBox().intersects(player.getCollisionBox())) {
					if(!illuminati.get(i).isDead()) player.hit(illuminati.get(i).getDamage());
				}
			}
		}
	}
	
	private void checkAttack() {
		player.checkAttack(illuminati);
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
		for (int i = 0; i < illuminati.size(); i++) {
			Enemy e = illuminati.get(i);
			e.update();
			if (e.isDead()) {
				illuminati.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety())); 
			}
		}
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
		bg.draw(g);
		tileMap.draw(g);
		drawEnemies(g);
		
		m1.draw(g);
		m2.draw(g);
		m3.draw(g);
		m4.draw(g);
		
		player.draw(g);
		hud.draw(g);
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(),(int) tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		m.draw(g);
	}
	
	private void drawEnemies(Graphics2D g) {
		for (int i = 0; i < illuminati.size(); i++) {
			illuminati.get(i).draw(g);
		}
		for (int i = 0; i < trollers.size(); i++) {
			trollers.get(i).draw(g);
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
		
		if(k == KeyEvent.VK_SPACE) player.setUp(true);
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		
		
		if(k == KeyEvent.VK_SHIFT) player.setSprinting(true);
		if(k == KeyEvent.VK_S) player.setSmoking();
		if(k == KeyEvent.VK_ESCAPE && !PauseState.keyLock) {
			gsm.setPaused(true);
		}
			
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
