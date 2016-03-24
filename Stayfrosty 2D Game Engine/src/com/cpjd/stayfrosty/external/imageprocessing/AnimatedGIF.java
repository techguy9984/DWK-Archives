package com.cpjd.stayfrosty.external.imageprocessing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class AnimatedGIF {

	private Point position; // the position of gif on screen
	private BufferedImage[] frames; // the internal frames
	private GifDecoder d;
	private int numFrames = -1;
	private int currentFrame = -1;
	private boolean isReady = false;
	
	private int width;
	private int height;
	
	private Random r;
	private int xadjust;
	
	private boolean shouldAdjust;
	
	private boolean finished;
	
	public AnimatedGIF(int x, int y) {
		position = new Point();
		position.x = x;
		position.y = y;
		
		shouldAdjust = false;
		
		finished = false;
		
		r = new Random();
	}

	public void read(String animatedGIFFile) {
		BufferedImage size;
		try {
			size = ImageIO.read(getClass().getResourceAsStream(animatedGIFFile));
			width = size.getWidth();
			height= size.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		d = new GifDecoder();
		d.read(getClass().getResourceAsStream(animatedGIFFile));
		numFrames = d.getFrameCount();
		currentFrame = 0;
		frames = new BufferedImage[numFrames];
		for (int i = 0; i < numFrames; i++) {
			frames[i] = d.getFrame(i);
		}
		isReady = true;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setDescale(int descale) {
		width /= 2;
		height /= 2;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void setAdjust(boolean b) {
		this.shouldAdjust = b;
	}
	public void draw(Graphics2D g) {
		if (isReady) {
			g.drawImage(frames[currentFrame], position.x + xadjust, position.y, width, height, null);
		}
	}
	public void nextFrame() {
		if(shouldAdjust) xadjust = r.nextInt(5);
		
		++currentFrame;
		if (currentFrame >= numFrames) {
			currentFrame = 0;
			finished = true;
		}
	}
}
