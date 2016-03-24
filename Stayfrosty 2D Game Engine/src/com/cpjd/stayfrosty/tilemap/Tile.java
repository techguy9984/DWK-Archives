package com.cpjd.stayfrosty.tilemap;

import java.awt.image.BufferedImage;

/* Description
 *  Stores traits of tile types
 * 
 */
public class Tile {
	
	private BufferedImage image; // The tiles picture
	private int type; // What type of tile the tile is
	private int id; // The blocks id
	
	// tiles types
	public static final int NORMAL = 0; // Tile types, can be more types
	public static final int BLOCKED = 1;
	public static final int FATAL = 2;
	public static final int OBTAINABLE = 3;
	public static final int MOVING = 4;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public int getType() {
		return type;
	}
	
}
