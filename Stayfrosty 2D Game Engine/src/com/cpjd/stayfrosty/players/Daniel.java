package com.cpjd.stayfrosty.players;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.input.Keymap;
import com.cpjd.input.Keys;
import com.cpjd.input.Mouse;
import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.tools.Animation;

/* Daniel
 * -Track runner and hobbies enthusiast
 * 
 * Abilities
 * -Can move faster than other characters and has more agility for jumping, etc. double jump
 * -Rubik's cube attack or defense or something
 * -Juggle grenades and throw them at targets
 * -Projectile attack
 * 
 */
// REMOVE THIS WHEN DONE
@SuppressWarnings("unused")
public class Daniel extends Player {
	
	private final int[] NUM_FRAMES = {2,10,10,2,2};
	
	// Animation action ids
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	
	// Technical
	private GameStateManager gsm;
	
	public Daniel(TileMap tm, GameStateManager gsm) {
		super(tm);
		
		this.gsm = gsm;
		
		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 64;
		
		walkSpeed = 3.3;
		maxSpeed = 3.3;
		moveSpeed = walkSpeed;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 7.0;
		jumpStart = -7;
		stopJumpSpeed = 0.3;
		facingRight = true;
		
		health = maxHealth = 100;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/daniel.png"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 5; i++) {

				BufferedImage[] bi = new BufferedImage[NUM_FRAMES[i]];

				for (int j = 0; j < NUM_FRAMES[i]; j++) {

					if (i != 6) {
						bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					} else {
						bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
					}

				}

				sprites.add(bi);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(200);
	}

	public void update() {
		handleInput();
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		checkTileCollision();

		// Check for debug placement (mouse click)
		if(GamePanel.DEBUG) {
			if(Mouse.leftPressed) {
				setPosition((int)Math.abs(tileMap.getx()) + (Mouse.x / 2), (int)Math.abs(tileMap.gety()) + (Mouse.y / 2));
			}
		}
		
		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) { // 1 second of inviciblity
				flinching = false;
			}
		}

		// set animation
		if (dy > 0) {
			if (currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 64;
			}
		} else if (dy < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 64;
			}
		} else if (left || right) {
			if (currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 64;
			}
		} else {
			if (currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 64;
			}
		}
		animation.update();

		// set direction
		if (right) facingRight = true;
		if (left) facingRight = false;
	}
	private void handleInput() {
		if(Keys.isPressed(Keymap.right)) setRight(true);
		if(Keys.isPressed(Keymap.left)) setLeft(true);
		if(Keys.isPressed(Keymap.back)) setDown(true);
		if(Keys.isPressed(Keymap.jump) || Keys.isPressed(Keymap.forward)) setJumping(true);

		if(!Keys.isPressed(Keymap.right)) setRight(false);
		if(!Keys.isPressed(Keymap.left)) setLeft(false);
		if(!Keys.isPressed(Keymap.back)) setDown(false);
		if(!Keys.isPressed(Keymap.jump) && !Keys.isPressed(Keymap.forward)) setJumping(false);
	}
}
