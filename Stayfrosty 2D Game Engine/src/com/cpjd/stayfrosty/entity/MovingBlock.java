package com.cpjd.stayfrosty.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.tilemap.TileMap;

@SuppressWarnings("unused")
public class MovingBlock extends Sprite {

	private BufferedImage block;
	
	// Starting location
	private int startx;
	private int starty;
	
	// How far the block travels
	private int xDist;
	//private int yDist;
	
	private int motionRange;
	
	Rectangle leftb;
	Rectangle rightb;
	Rectangle upb;
	Rectangle downb;
	
	
	public MovingBlock(TileMap tm, int startx, int starty, int motionRange) {
		super(tm);
		
		this.startx = startx;
		this.starty = starty;
		
		this.motionRange = motionRange;
		
		setPosition(startx, starty);
		
		width = 64;
		height = 64;
		cwidth = 64;
		cheight = 64;
		
		moveSpeed = 2.5;
		maxSpeed = 2.5;
		
		try {
			BufferedImage tiles = ImageIO.read(getClass().getResourceAsStream("/Tilesets/tileset.png"));
			block = tiles.getSubimage(0, 256, 64, 64);
		} catch(IOException e) {
			
		}
		
		right = true;
		facingRight = left;
	}

	public void setMoveSpeed(double ms) {
		this.moveSpeed = ms;
	}
	
	public void checkPlayerCollision(Player player) {
		Rectangle mPlayer = new Rectangle(player.getCollisionBox().x,player.getCollisionBox().y  + player.getCollisionBox().height ,player.getCollisionBox().width,10);
		Rectangle mBlock = new Rectangle(getCollisionBox().x, getCollisionBox().y + 10, getCollisionBox().width, 1);
		if(mPlayer.intersects(mBlock)) {
			player.setDownC(true);
		} else {
			player.setDownC(false);
		}
	}
	
	private void getNextPosition() {
		// Moves back and forth horizontally, changes direction upon collision
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} 

	}

	public void update() {
		// update pos
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		// Check for our range of motion
		if(right && x > startx + motionRange) {
			right = false;
			left = true;
			facingRight = false;
		}
		
		else if(left && x < startx) {
			right = true;
			left = false;
			facingRight = true;
		}
		
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		g.drawImage(block, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		
		// Super method isn't called, so we use this
		if(GamePanel.DEBUG) {
			g.setColor(Color.RED);
			g.drawRect(getCollisionBox().x, getCollisionBox().y, getCollisionBox().width, getCollisionBox().height);
			
		}
			
	}

}
