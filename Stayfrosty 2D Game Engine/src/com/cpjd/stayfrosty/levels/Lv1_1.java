package com.cpjd.stayfrosty.levels;

import java.awt.Graphics2D;

import com.cpjd.stayfrosty.entity.HUD;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.players.Daniel;
import com.cpjd.stayfrosty.tilemap.Background;
import com.cpjd.stayfrosty.tilemap.TileMap;

public class Lv1_1 extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private Daniel daniel;
	private HUD hud;
	
	public Lv1_1(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new TileMap(64);
		tileMap.load64("/Tilesets/tileset.png");
		tileMap.loadTiledMap("/Maps/tutorial.txt");
		tileMap.setPosition(300, 1150);
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/wood.png",0.1);
		
		hud = new HUD(daniel);
		
		daniel = new Daniel(tileMap, gsm);
		daniel.setPosition(190, 1150);
	}

	public void update() {
		daniel.update();
		
		hud.update();
		
		tileMap.setPosition(GamePanel.WIDTH / 2 - daniel.getx(), GamePanel.HEIGHT / 2 - daniel.gety()); 
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		daniel.draw(g);
		hud.draw(g);
	}

	public void keyPressed(int k) {
		
	}

	public void keyReleased(int k) {
	}

}
