package com.cpjd.stayfrosty.levels;

public class Example_Template {
/*	
	private TileMap tileMap; 
	private Background bg;
	private Player player;

	private HUD hud;
	
	private ArrayList<Explosion> explosions;
	
	public Example_Template(GameStateManager gsm) {
		super(gsm);
		
		init();
	}
	
	public void init() {
		tileMap = new TileMap(64);
		tileMap.load64("/Tilesets/tileset.png");
		tileMap.loadTiledMap("/Maps/");
		tileMap.setPosition(300,1700);
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/wood.png",0.1);

		player = new Player(tileMap, gsm);
		
		player.setPosition(300, 1700);
		
		populateEnemies();
		
		hud = new HUD(player); 

		explosions = new ArrayList<Explosion>();

			
	}
	private void populateEnemies() {}
	
	public void update() {
		player.update();
		
		hud.update();		
		
		checkMelee();
		
		bg.setPosition(tileMap.getx(),tileMap.gety());
		
		if(!Player.freecam) tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); 
		
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
	
	private void checkAttack() {}
	
	private void checkMelee() {}
	
	private void removeEnemies() {}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		drawEnemies(g);
		hud.draw(g);
		player.draw(g);
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(),(int) tileMap.gety());
			explosions.get(i).draw(g);
		}
	}
	
	private void drawEnemies(Graphics2D g) {}
	
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
	*/
}
