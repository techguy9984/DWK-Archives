package com.cpjd.stayfrosty.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;

/* Description
 *  Stores all required methods for each state (see GameStateManager)
 * Methods Descpription
 *  update() is called repeately when the state is set to active
 *  draw(Graphics2D g) is called repeately when the state is set to active
 *  constructor is called when the state becomes active
 *  handleInput contains all the input code
 */
public abstract class GameState {

	protected GameStateManager gsm;
	protected Load load;
	protected Save save;
	
	protected BufferedImage loading;
	
	
	public GameState(GameStateManager gsm, Load load, Save save) {
		this.gsm = gsm;
		this.load = load;
		this.save = save;
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
}
