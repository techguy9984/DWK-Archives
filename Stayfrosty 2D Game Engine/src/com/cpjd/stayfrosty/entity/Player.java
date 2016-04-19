package com.cpjd.stayfrosty.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;

@SuppressWarnings("unused")
public class Player extends Sprite {

	public static int health;
	public static int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;

	// Armor
	public static double armor;
	public static int maxArmor;
	private double armorRegen = 0.005;

	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 2, 8, 1, 2, 2 };

	public static double globalX;
	public static double globalY;

	public static boolean globalDead;

	public boolean lockMovement;

	public boolean munlocked; // The mystery box

	// This arraylist stores all of the players jumps at every location
	public static ArrayList<Point> jumps;

	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int CROUCHING = 4;

	private boolean cCrouching;

	// Sprinting psyhics
	private boolean sprinting;
	private double walkSpeed; // The speed the player walks
	private double sprintSpeed; // How fast the player can sprint
	private double maxEndurance; // While the player is sprinting, it uses up
									// endurance
	private double currentEndurance; // The current endurance
	private double enduranceGain; // The rate at which endurance is gained
	private double enduranceLost; // The rate at which endurance is lost

	// Powerup psyhics
	private boolean powerupActivated;
	private double powerupSpeed; // How fast the player can move while powerup
									// is activated
	private double powerupJump; // Extra jump for powerup
	// Timing
	public static double currentPowerup;
	public static double powerupRequired = 10; // Amount of powerups required to
												// activated the powerup
	// everyday
	private double powerupLost = 0.01; 

	public static double currentMemes; // public so the shop can access it

	public static boolean completedLevel; // Has the player completed the
											// current level?

	// Shield
	private static boolean shield;
	private static double currentShield;
	private static double shieldMax;
	private static double shieldDecay;
	private double orbx;
	private double orby;
	private double degree;

	public static boolean refill;

	// MLG Stuff
	BufferedImage shades;
	private int shadeOffset = 15;
	private double counter = 10;
	boolean foundPlayer = false;
	private int tempEquip;

	// Doritoes
	public static int currentDoritoes;

	// Tiles
	public int pendingPowerupTiles; // Amount of powerup tiles that we collided with
									// and removed
	public int pendingDankMemes;
	public int pendingDoritoes;

	public static boolean freecam;

	// Game State
	private GameStateManager gsm;

	// Loading screen
	BufferedImage loading;

	// Fancy pickup animations
	ArrayList<Zoom> zooms;

	public Player(TileMap tm, GameStateManager gsm) {

		super(tm);

		this.gsm = gsm;


		currentMemes = 0;

		width = 64;
		height = 64;
		cwidth = 32;
		cheight = 64;

		tag = "Player";

		walkSpeed = 3.3;
		sprintSpeed = 7.5;
		currentEndurance = 10;
		maxEndurance = 10;
		enduranceGain = 0.01;
		enduranceLost = 0.03;
		moveSpeed = walkSpeed;
		maxSpeed = 3.3;

		currentPowerup = 0;

		armor = 0;
		maxArmor = 0;

		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 7.0;
		jumpStart = -7;
		stopJumpSpeed = 0.3;

		facingRight = true;

		health = maxHealth = 100;

		jumps = new ArrayList<Point>();

		cCrouching = false;

		lockMovement = false;

		shieldMax = 10;
		shieldDecay = 0.007;

		zooms = new ArrayList<Zoom>();

		// load sprites
		try {
			shades = ImageIO.read(getClass().getResourceAsStream("/MLG/glasses.png"));

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/bryan.gif"));

			loading = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/wood.png"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 5; i++) {

				BufferedImage[] bi = new BufferedImage[numFrames[i]];

				for (int j = 0; j < numFrames[i]; j++) {

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

		degree = 0;

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);

		munlocked = false;
	}

	public static void activeShield() {
		currentShield = shieldMax;
		shield = true;
	}

	public boolean isSmoking() {
		return powerupActivated;
	}

	public double getEndurance() {
		return currentEndurance;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public double getMaxEndurance() {
		return maxEndurance;
	}

	public int getFire() {
		return fire;
	}

	public double getMemes() {
		return currentMemes;
	}

	public int getMaxFire() {
		return maxFire;
	}

	public void setSprinting(boolean b) {
		if (b)
			maxSpeed = sprintSpeed;
		if (!b)
			maxSpeed = walkSpeed;
		sprinting = b;
	}

	public void slow() {

		maxEndurance = 0;
		currentEndurance = 0;
		walkSpeed = 0.5;

	}

	public void resetSlow() {
		currentEndurance = maxEndurance = 10;
		walkSpeed = 3.3;
	}

	public void setSmoking() {
		if (currentPowerup >= powerupRequired) {
			health = 420;
			currentEndurance = 420;
			powerupActivated = true;
			jumpStart = -10;
		}
	}

	public double getCurrentPowerups() {
		return currentPowerup;
	}

	public void addPowerup(double powerup) {
		currentPowerup += powerup;
		if (currentPowerup > powerupRequired)
			currentPowerup = powerupRequired;
	}

	public void removeAllPowerups() {
		currentPowerup = 0;
	}

	public void removeMemes(int amount) {
		currentMemes -= amount;
		if (currentMemes < 0)
			currentMemes = 0;
	}

	public void removeDoritoes(int amount) {
		currentDoritoes -= amount;
		if (currentDoritoes < 0)
			currentDoritoes = 0;
	}

	// Checks for our attacks on enemies
	public void checkAttack(ArrayList<Enemy> enemies) {

	}

	public void hit(int damage) {
		if (GamePanel.DEBUG) {
			return;
		}
		if (shield) {
			return;
		}
		if (flinching)
			return; // Don't get hit while flinching
		if (!powerupActivated)
			AudioPlayer.playSound(SKeys.Damage);

		// First check armor
		if (armor > 0) {
			armor -= damage;
			int temp = 0;
			if (armor < 0) {
				temp = (int) armor;
				health += armor;
				armor = 0;
			}
		} else {
			health -= damage;
		}

		if (health < 0)
			health = 0;
		if (health == 0) {
			dead = true;
			globalDead = true;
		}
		flinching = true;
		flinchTimer = System.nanoTime();

	}

	public void stopSmoking() {
		if (gsm.getState() <= 13)
			AudioPlayer.loopMusic(SKeys.Main);
		if (gsm.getState() >= 16 && gsm.getState() < 25)
			AudioPlayer.loopMusic(SKeys.Set_2);
		if (gsm.getState() >= 25)
			AudioPlayer.loopMusic(SKeys.Set_3);
		powerupActivated = false;
		currentEndurance = maxEndurance;
		health = maxHealth;
		jumpStart = -7;
		currentPowerup = 0;
	}

	private void getNextPosition() {

		if (lockMovement)
			return;

		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		// jumping
		if (jumping && !falling) {
			cCrouching = false;
			jumps.add(new Point(getx(), gety()));

			dy = jumpStart;
			if (!verticalEnabled)
				falling = true;
		}

		// falling
		if (falling) {
			cCrouching = false;
			if (!verticalEnabled)
				dy += fallSpeed;

			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping)
				if (!verticalEnabled)
					dy += stopJumpSpeed;

			if (dy > maxFallSpeed)
				if (!verticalEnabled)
					dy = maxFallSpeed;

		}

	}

	public void setMovementLock(boolean b) {
		this.lockMovement = b;
	}

	// All obtainable collisions
	private void checkTileCollision() {

		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;

		if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			hit(100);
			return;
		}
		// NOTE
		// In the map, powerup id is 93
		// Here, the collision id is one less

		// Check for doritoes collision
		if (tileMap.getID(topTile, leftTile) == 90) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(topTile, leftTile, 0);
			pendingDoritoes++;
			return;
		} else if (tileMap.getID(topTile, rightTile) == 90) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(topTile, rightTile, 0);
			pendingDoritoes++;
			return;

		} else if (tileMap.getID(bottomTile, leftTile) == 90) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(bottomTile, leftTile, 0);
			pendingDoritoes++;
			return;

		} else if (tileMap.getID(bottomTile, rightTile) == 90) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(bottomTile, rightTile, 0);
			pendingDoritoes++;
			return;

		}

		// Check for keys collision
		if (tileMap.getID(topTile, leftTile) == 96) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(topTile, leftTile, 0);
			removeCrates();
			return;
		} else if (tileMap.getID(topTile, rightTile) == 96) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(topTile, rightTile, 0);
			removeCrates();
			return;

		} else if (tileMap.getID(bottomTile, leftTile) == 96) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(bottomTile, leftTile, 0);
			removeCrates();
			return;

		} else if (tileMap.getID(bottomTile, rightTile) == 96) {
			AudioPlayer.playSound(SKeys.Collect);
			tileMap.setMap(bottomTile, rightTile, 0);
			removeCrates();
			return;

		}

		// Checks for police station
		if (tileMap.getID(topTile, leftTile) == 4 || tileMap.getID(topTile, leftTile) == 5) {
			AudioPlayer.playSound(SKeys.Collect);
			stopSmoking();
			return;
		} else if (tileMap.getID(topTile, rightTile) == 4 || tileMap.getID(topTile, leftTile) == 5) {
			AudioPlayer.playSound(SKeys.Collect);
			stopSmoking();
			return;

		} else if (tileMap.getID(bottomTile, leftTile) == 4 || tileMap.getID(topTile, leftTile) == 5) {
			AudioPlayer.playSound(SKeys.Collect);
			stopSmoking();
			return;

		} else if (tileMap.getID(bottomTile, rightTile) == 4 || tileMap.getID(topTile, leftTile) == 5) {
			AudioPlayer.playSound(SKeys.Collect);
			stopSmoking();
			return;

		}

		// Checks for portals
		if (tileMap.getID(topTile, leftTile) == 99) {
			nextLevel();
			return;
		} else if (tileMap.getID(topTile, rightTile) == 99) {
			nextLevel();
			return;
		} else if (tileMap.getID(bottomTile, leftTile) == 99) {
			nextLevel();
			return;
		} else if (tileMap.getID(bottomTile, rightTile) == 99) {
			nextLevel();
			return;
		}
		if (munlocked)
			return;
		// Mystery box
		if (tileMap.getID(topTile, leftTile) == 95 && !checked) {
			question();
			return;
		} else if (tileMap.getID(topTile, rightTile) == 95 && !checked) {
			question();
			return;
		} else if (tileMap.getID(bottomTile, leftTile) == 95 && !checked) {
			question();
			return;
		} else if (tileMap.getID(bottomTile, rightTile) == 95 && !checked) {
			question();
			return;
		} else if (tileMap.getID(bottomTile, rightTile) != 95 && tileMap.getID(bottomTile, leftTile) != 95
				&& tileMap.getID(topTile, rightTile) != 95 && tileMap.getID(topTile, rightTile) != 95) {
			checked = false;
		}
	}

	boolean checked;

	private void question() {
		
		checked = true;

		try {
			String input = JOptionPane.showInputDialog(null, "What is 1 + 1?", "Question:",
					JOptionPane.INFORMATION_MESSAGE);
			if (input.equalsIgnoreCase("2")) {
				JOptionPane.showMessageDialog(null, "Correct!");
				removeCrates();
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incorrect!");
		}
		// Fix key inputs
		setRight(false);
		setLeft(false);
	}

	public void removeCrates() {
		for (int i = 0; i < tileMap.getNumCols(); i++) {
			for (int j = 0; j < tileMap.getNumRows(); j++) {
				int[][] tempMap = tileMap.getMap();
				if (tempMap[j][i] == 40) {
					tileMap.setMap(j, i, 0);
				}
			}
		}
	}

	private void nextLevel() {
		if (currentDoritoes >= gsm.getState() - 4
				|| gsm.getState() == GameStateManager.L_TUTORIAL && gsm.getState() <= 14) {
			currentDoritoes = 0;
			stopSmoking();
			Player.completedLevel = true;
		}
		if (currentDoritoes >= gsm.getState() - 15 && gsm.getState() >= 15 && gsm.getState() < 26) {
			currentDoritoes = 0;
			stopSmoking();
			Player.completedLevel = true;
		}
		if (currentDoritoes >= gsm.getState() - 26 && gsm.getState() >= 26) {
			currentDoritoes = 0;
			stopSmoking();
			Player.completedLevel = true;
		}
	}

	public void update() {

		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);


		// Calculate shield orbital position
		degree += 0.09;
		if (degree >= 360)
			degree = 0;
		double centerx = (x + xmap - width / 2) - 16 + 50;
		double centery = (y + ymap - height / 2) - 20 + 50;
		orbx = centerx + 50 * Math.cos(degree);
		orby = centery + 50 * Math.sin(degree);

		// Update zooms
		for (int i = 0; i < zooms.size(); i++) {
			zooms.get(i).update();
			if (zooms.get(i).isFinished()) {
				zooms.remove(i);
			}
		}

		// Check tile collision
		checkTileCollision();

		// Shield
		if (shield) {
			currentShield -= shieldDecay;
			if (currentShield < 0) {
				AudioPlayer.stopSound(SKeys.Shield_Running);
				shield = false;
			}
		}

		// Update armor
		if (maxArmor > 0) {
			armor += armorRegen;
			if (armor > maxArmor)
				armor = maxArmor;
		}

		// animations for pickups
		if (pendingPowerupTiles > 0) {
			for (int i = 0; i < pendingPowerupTiles; i++) {
				zooms.add(new Zoom("/Icons/powerup.png", (int) (x + xmap - width / 2), (int) (y + ymap - height / 2)));
			}
		}

		// check for pending powerup tiles
		currentPowerup += (pendingPowerupTiles * 2);
		if (currentPowerup > powerupRequired)
			currentPowerup = powerupRequired;
		pendingPowerupTiles = 0;

		// Doritoes
		if (pendingDoritoes > 0) {
			for (int i = 0; i < pendingDoritoes; i++) {
				zooms.add(new Zoom("/Icons/dorito.png", (int) (x + xmap - width / 2), (int) (y + ymap - height / 2)));
			}
		}
		currentDoritoes += (pendingDoritoes * 1);
		pendingDoritoes = 0;

		// check for pending dank memes
		if (pendingDankMemes > 0) {
			for (int i = 0; i < pendingDankMemes; i++) {
				zooms.add(new Zoom("/Icons/meme.png", (int) (x + xmap - width / 2), (int) (y + ymap - height / 2)));
			}
		}

		currentMemes += pendingDankMemes;
		pendingDankMemes = 0;

		// check if player is dead
		if (dead) {
			dead = false;
			Player.currentDoritoes = 0;
			gsm.setState(gsm.getState());
		}

		// Sprinting
		// The uses is already sprinting, this function just controls how long
		// they can sprint
		if (sprinting) {
			if (currentEndurance <= 1) {
				maxSpeed = walkSpeed;
				sprinting = false; // Stop sprinting the player runs out of
									// endurance
			}
			// Endurance lost
			if (currentEndurance > 0) { // Only sprint if there is enough
										// endurance
				if (!GamePanel.DEBUG)
					currentEndurance -= enduranceLost;
			}
		}
		if (!sprinting) {
			// Endurance generated
			if (currentEndurance < maxEndurance) {
				currentEndurance += enduranceGain;
			}
		}

		// Update powerup
		if (powerupActivated) {
			if (currentPowerup <= 0) {
				stopSmoking();
			}
			if (currentPowerup > 0)
				currentPowerup -= powerupLost;
		}

		// Deadly block
		if (hitingFatalBlock) {
			hit(1);
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
		} else if (cCrouching) {
			if (currentAction != CROUCHING) {
				currentAction = CROUCHING;
				animation.setFrames(sprites.get(CROUCHING));
				animation.setDelay(70);
				width = 64;
			}
		}

		else {
			if (currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 64;
			}
		}

		Player.globalX = x;
		Player.globalY = y;

		animation.update();

		// set direction

		if (right)
			facingRight = true;
		if (left)
			facingRight = false;

	}

	@Override
	public void draw(Graphics2D g) {

		setMapPosition();

		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		// Zoom
		for (int i = 0; i < zooms.size(); i++) {
			zooms.get(i).draw(g);
		}

		super.draw(g);

		if (shield) {
			g.setColor(Color.CYAN);
			Stroke tempStroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.drawOval((int) (x + xmap - width / 2) - 16, (int) (y + ymap - height / 2) - 20, 100, 100);
			// Orb
			g.setColor(Color.BLUE);
			g.fillOval((int) orbx - 1, (int) orby - 1, 5, 5);
			g.setStroke(tempStroke);
		}

		if (powerupActivated) { // Draw glasses

			if (counter > 0) {
				counter--;
				shadeOffset--;
			}
			if (facingRight)
				g.drawImage(shades, (int) (x + xmap - width / 2) + shades.getWidth(),
						(int) ((y + ymap - height / 2) + 6) - shadeOffset + 3, null);
			if (!facingRight)
				g.drawImage(shades, (int) (x + xmap - width / 2) + shades.getWidth() * 2 - 9,
						(int) ((y + ymap - height / 2) + 6) - shadeOffset + 3, -shades.getWidth(), shades.getHeight(),
						null);
		}

	}

	public void keyPressed(int k) {

		if (k == KeyEvent.VK_UP && verticalEnabled) {
			dy = -moveSpeed;
		}
		if (k == KeyEvent.VK_DOWN && verticalEnabled) {
			dy = moveSpeed;
		}
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_UP && verticalEnabled) {
			dy = 0;

		}
		if (k == KeyEvent.VK_DOWN && verticalEnabled) {
			dy = 0;

		}
	}

}
