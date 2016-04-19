package com.cpjd.stayfrosty.tilemap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Center;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax; 
	
	// Tween
	private double tween; // Smoothly follows the player instead of rugged
	
	// map
	private int[][] map; // The actual map
	private int tileSize; // How many pixels each tile uses
	private int numRows; // The number of tiles vertically
	private int numCols; // The number of tiles horizontally
	private int width; // The complete width of the map
	private int height; // The complete height of the map
	
	// Tileset
	private BufferedImage tileset; // Complete tileset file, Stores the individual images for each tile
	private int numTilesAcross; // Width of the actual tileset png image (in tiles) 
	private Tile[][] tiles; // respresentation of tileset
	
	
	// drawing - only draws the tiles we are looking at for performance
	private int rowOffset; // Which row to start drawing
	private int colOffset; // Which column to start drawing
	private int numRowsToDraw; // How many rows to draw
	private int numColsToDraw; // The number of tiles to draw
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2; // Draw 8 tiles, 2 is for the padding
		numColsToDraw = GamePanel.WIDTH / tileSize + 2; // Draw a bunch across
		tween = 0.07; // Smooth-scrolling

	}
	
	public void loadTiles(String s) {
		
		try {
			
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize; // 630 (tileset width) / 30 = 21 tiles (we have 21 tiles that need to be loaded)
			tiles = new Tile[2][numTilesAcross]; // 2 rows high, 21 columns across. Gets the images we need for the different tiles
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) { // Runs the loop through all of the tiles in our tileset
				subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize); // Gets the small individual tiles from the tileset
				tiles[0][col] = new Tile(subimage,Tile.NORMAL); // Stores the actual tile image in an assigned location acording to it's position on the png file
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage,Tile.BLOCKED);
				// NORMAL - we can pass through it
				// BLOCKED - we can't pass through it
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void load64(String s) {
		
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[5][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) { // Runs the loop through all of the tiles in our tileset
				subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize); // Gets the small individual tiles from the tileset
				tiles[0][col] = new Tile(subimage,Tile.NORMAL); // Stores the actual tile image in an assigned location acording to it's position on the png file
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage,Tile.BLOCKED);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 2, tileSize, tileSize);
				tiles[2][col] = new Tile(subimage,Tile.FATAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
				tiles[3][col] = new Tile(subimage,Tile.OBTAINABLE);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 4, tileSize, tileSize);
				tiles[4][col] = new Tile(subimage,Tile.MOVING);
				
			
				// NORMAL - we can pass through it
				// BLOCKED - we can't pass through it
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			numCols = Integer.parseInt(br.readLine()); // These two variables tell us the dimensions of our text file, they are required for the loop
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize; // The dimensions of the map, in tilesize (in pixels)
			height = numRows * tileSize; 
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+"; // Ignores space between tokens FIXME
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine(); // Save the whole line into memory
				String[] tokens = line.split(delims); // Split it apart by spaces and but it into a string array
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadTiledMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			br.readLine(); // Skip header tag
			String delim = "width="; String[] tokens = br.readLine().split(delim);
			numCols = Integer.parseInt(tokens[1]);
			delim = "height="; tokens = br.readLine().split(delim);
			numRows = Integer.parseInt(tokens[1]);
			
			map = new int[numRows][numCols];
			width = numCols * tileSize; // The dimensions of the map, in tilesize (in pixels)
			height = numRows * tileSize; 

			for(int i = 0; i < 10; i++) {
				br.readLine(); // We don't really need any of the other information
			}
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			// Read in the map
			String delims = ","; // Ignores space between tokens FIXME
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine(); // Save the whole line into memory
				tokens = line.split(delims); // Split it apart by spaces and but it into a string array
				for(int col = 0; col < numCols; col++) {
					int tile = Integer.parseInt(tokens[col]);
					if(tile != 0) tile--;
					map[row][col] = tile;
				}
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Getters
	public int getTileSize() {
		return tileSize;
	}
	public int[][] getMap() {
		return map;
	}
	
	public void setMap(int row, int col, int value) {
		map[row][col] = value;
	}
	
	public double getx() {
		return x;
	}
	public double gety() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setTween(double tween) {
		this.tween = tween;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumCols() {
		return numCols;
	}
	
	public int getType(int row, int col) { // FIXME 
		int rc = map[row][col]; // The type of tile, the VALUE in the map file based on the amounts of images. 0,1,2,3,4 etc.
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross; // Getting the tile type based on our config
		return tiles[r][c].getType(); // The tile located at a certain position in the png image
		
		// c doesn't matter 
	}
	
	public int getID(int row, int col) {
		int rc = map[row][col];
		return rc;
	}
	
	public void setPosition(double x, double y) {

		// TWEEN - camera gradullally shifts to the player
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		// FIXME 
		
		// WHERE WE ARE GOING TO START DRAWING:
		colOffset = (int)-this.x / tileSize; // Makes sure we are drawing on the screen
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	private void fixBounds() { // Makes sure we aren't crosses any boundries
		if(x < xmin) x = xmin;
		if(x > xmax) x = xmax;
		if(y < ymin) y = ymin;
		if(y > ymax) y = ymax;
		
	}
	
	public void draw(Graphics2D g) {
		// Draw tiles
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if(col >= numCols) break;
				
				if(map[row][col] == 0) continue; // Skips to the next loop
				
				// If the id is greater than width, it's goes to the next item
				// Right to left, up to down
				int rc = map[row][col]; // FInd out which tile to draw, giving ids to the pictures
				int r = rc / numTilesAcross; /* This formula takes the id in the map file and uses to it get two numbers to describe
				a picture in the png file */
				int c = rc % numTilesAcross; // 4 goes in zero times, so the answer is four
				g.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize,(int)y + row * tileSize,null);
			}
		}
		// Draw coordinates
		if (GamePanel.DEBUG) {
			int tempx = (int)Math.abs(getx()) + (4 / 2);
			int tempy = (int)Math.abs(gety()) + (4/ 2);
			g.setColor(Color.GREEN);
			g.fillRect(5, GamePanel.HEIGHT - 26, Center.getSWidth(g,"XY: "+"(" + tempx + "," + tempy + ")"), 13);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.drawString("XY: " + "(" + tempx + "," + tempy + ")", 5, GamePanel.HEIGHT - 15);
			
		}
				
	}
	
}
