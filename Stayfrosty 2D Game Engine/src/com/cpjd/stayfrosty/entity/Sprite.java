package com.cpjd.stayfrosty.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.Tile;
import com.cpjd.stayfrosty.tilemap.TileMap;
import com.cpjd.tools.Animation;

/* Everything that's going to be in the class */
public abstract class Sprite {

	/* Everymap object has ALL of these fields */
	
	// tile stuff
	protected TileMap tileMap; // Need access to the tilemap class, it gets passed into this class
	protected int tileSize; // size of one tile (pixels)
	protected double xmap;
	protected double ymap;
	
	// moving block
	protected boolean leftC;
	protected boolean rightC;
	protected boolean upC;
	protected boolean downC; // If the player is currently intersecting with a moving block
	protected boolean downF; // If the collision request has already been processed
	
	protected boolean verticalEnabled; // For ladder climbing
	
	// position and vector
	protected double x; // The location of the object
	protected double y;
	protected double dx; // Direction it's going
	protected double dy;
	
	// dimensions
	protected int width; // Mainly for reading in sprite sheets
	protected int height;
	
	// Sprite tag
	protected String tag;
	
	// collision box
	protected int cwidth; // Locating a collision box, real dimensions
	protected int cheight;
	
	// collision - other
	protected int currRow; // Current location on map
	protected int currCol;
	protected double xdest; // Location of where we want to go
	protected double ydest;
	protected double xtemp; // Temporary position variables
	protected double ytemp;
	// 4 corners method
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation stuff
	protected Animation animation; // For use with our animation class
	protected int currentAction; // which animation we are currently used
	protected int previousAction; // The last action we were performing
	protected boolean facingRight; // Where we want to face, (only right or left), if it's facing left, we have to flip it
	
	// movement
	protected boolean left; // What the object is actually doing
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// movement attributes, psychics
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed; // Deceleration speed, if you aren't pressing anything, how fast do you slow down
	protected double fallSpeed; // Gravity
	protected double maxFallSpeed; // Terminal velocity
	protected double jumpStart; // How high the object can jump
	protected double stopJumpSpeed; // holding jump button for longer you go higher
	
	protected boolean hitingFatalBlock;
	

	// constructor
	public Sprite(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	public boolean intersects(Sprite o) {
		// rect collsion - checks if this instance is intersecting with the instance passed into it
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2); // Return true if they have collided, fireballs, projectiles, etc. 
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth,(int)y- cheight,cwidth,cheight);
	}
	
	public Rectangle getCollisionBox() {
		return new Rectangle((int) (x + xmap - cwidth / 2),(int) (y + ymap - cheight / 2), cwidth, cheight);
	}
	
	public void setDownC(boolean b) {
		downC = b;
	}
	
	public void setUpC(boolean b) {
		upC = b;
	
	}
	public void setLeftC(boolean b) {
		leftC = b;
	}
	public void setRightC(boolean b) {
		rightC = b;
	}
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		
		
		int tl = tileMap.getType(topTile, leftTile); // Types of the tiles around us
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		if(tl == Tile.FATAL || tr == Tile.FATAL || bl == Tile.FATAL || br == Tile.FATAL) {
			hitingFatalBlock = true;
		} else {
			hitingFatalBlock = false;
		}
		
		
		
		topLeft = tl == Tile.BLOCKED; // Check if it's blocked
		// if the tl value is equal to being blocked, set topLeft collision to true
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}
	
	public void checkTileMapCollision() { // Check if we are running into blocked or normal tile
		currCol = (int)x / tileSize; // Location in tilesize
		currRow = (int)y / tileSize;
		xdest = x + dx; // Destination position
		ydest = y + dy;
		
		xtemp = x; // Keep track of original x
		ytemp = y;
		
		calculateCorners(x,ydest); // Four cornered method - in y direction
		if(dy < 0) { // Going upwards
			if(topLeft || topRight || upC) { // Top too corners
				dy = 0; // STop it from moving
				ytemp = currRow * tileSize + cheight / 2; // Set's us right below tile we bumped our head into
			} else {
				ytemp += dy; // If nothing is stopping us, keep going up
			}
		}
		if(dy > 0) { // Landed on a tile
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			} else if (!downC ){
				ytemp += dy; // Keep falling if there is nothing there
			}
			
		}
		
		// Moving block collision
		if(downC) {
			if(falling && dy > 0) {
				falling = false;
				dy = 0;
			}
		}
		
		calculateCorners(xdest,y);
		if(dx < 0) { // We are going left
			if(topLeft || bottomLeft || leftC) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			} else {
				xtemp += dx;
			}
		}
		if(dx > 0) { // Moving to the right
			if(topRight || bottomRight || rightC) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2; // Sets us just to the left
			} else {
				xtemp += dx;
			}
		}
		
		// Did we just run off a cliff
		if(!falling) {
			calculateCorners(x,ydest + 1);
				// Make sure we didn't fall of a cliff
			if(!bottomLeft && !bottomRight) {
				// We aren't standing on solid ground!!!
				if(!downC) falling = true;
			}
		}
		
	}
	
	public int getx() {
		return (int)x;
	}
	public int gety() {
		return (int)y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getCWidth() {
		return cwidth;
	}
	public int getCHeight() {
		return cheight;
	}
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public boolean getDownF() {
		return downF;
	}
	// Global position (regular x & y), local position (+ tileMap pos)
	public void setMapPosition() {
		xmap = tileMap.getx(); // Map pos tells us where to draw, actual location! vs global!, only want to draw it if it enter the screen
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) {
		left = b;
	}
	public void setRight(boolean b) {
		right = b;
	}
	public void setUp(boolean b) {
		up = b;
	}
	public void setDown(boolean b) {
		down = b;
	}
	public void setJumping(boolean b) { 
		if(!verticalEnabled) jumping = b;
	}
	
	// Don't draw map objects that arne't on the screen
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}
	
	
	public void draw(Graphics2D g) {
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		} else {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
					-width, height, null);
		}
		
		if(GamePanel.DEBUG) {
			g.setColor(Color.RED);
			g.drawRect(getCollisionBox().x, getCollisionBox().y, getCollisionBox().width, getCollisionBox().height);
		}
	}

	
	
	
}
